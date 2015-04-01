package com.inhand.milk.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.inhand.milk.App;
import com.inhand.milk.entity.Base;
import com.inhand.milk.entity.OneDay;
import com.inhand.milk.entity.Record;
import com.inhand.milk.helper.DBHelper;
import com.inhand.milk.helper.JSONHelper;
import com.inhand.milk.utils.Calculator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * OneDayDao
 * Desc: 日记录数据访问层
 * Team: InHand
 * User: Wooxxx
 * Date: 2015-03-05
 * Time: 15:40
 */
public class OneDayDao extends BaseDao {
    private static final String SORT_BY = "createdAt";
    private AVQuery<OneDay> query;

    public OneDayDao(Context ctx) {
        super(ctx);
        query = new AVQuery<>(OneDay.ONEDAY_CLASS);
    }


    @Override
    public void findAllFromCloud(int limit, FindCallback callback) {
        // 按照更新时间降序排序
        query.whereEqualTo("baby", App.getCurrentBaby().getObjectId());
        query.orderByDescending(SORT_BY);
        // 最大返回1000条
        if (limit > 0)
            query.limit(limit);
        query.findInBackground(callback);
    }

    @Override
    public List<OneDay> findAllFromCloud(int limit) {
        List<OneDay> oneDays = new ArrayList<>();
        query.whereEqualTo("baby", App.getCurrentBaby().getObjectId());
        query.orderByDescending(SORT_BY);
        if (limit > 0)
            query.limit(0);
        try {
            oneDays = query.find();
            Log.d("find size:", String.valueOf(oneDays.size()));
        } catch (AVException e) {
            e.printStackTrace();
        }
        return oneDays;
    }

    /**
     * 异步地根据日期从云端获取某日记录
     *
     * @param date     查询日期
     * @param callback 回调函数
     */
    public void findOneDayFromCloud(String date, FindCallback callback) {
        query.whereEqualTo(OneDay.DATE_KEY, date);
        query.findInBackground(callback);
    }

    /**
     * 同步地根据日期从云端获取某日记录
     *
     * @param date
     * @return 查询到的对象
     */
    public OneDay findOneDayFromCloud(String date) {
        query.whereEqualTo(OneDay.DATE_KEY, date);
        try {
            List<OneDay> days = query.find();
            if (days.size() > 0)
                return days.get(0);
            else
                return null;
        } catch (AVException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void updateOrSaveInCloud(Base oneDay) throws AVException {
        OneDay src = (OneDay) oneDay;
        OneDay old = findOneDayFromCloud(src.getDate());
        if (old == null) {
            //不存在则新建
            src.save();
        } else {
            merge(src, old);
            //云端更新
            old.save();
        }
    }

    @Override
    public void updateOrSaveInCloud(Base oneDay, FindCallback callback) {

    }

    @Override
    public List<OneDay> findAllFromDB(int limit) {
        List<OneDay> oneDays = new ArrayList<>();
        String count = limit > 0 ? "0," + String.valueOf(limit) : null;
        String orderBy = "_id";
        //取出计数器
        Cursor cursor = this.db.query(
                OneDay.ONEDAY_CLASS,
                new String[]{DBHelper.COLUMN_JSON},
                null,
                null,
                null,
                null,
                orderBy,
                count
        );
        while (cursor.moveToNext()) {
            String json = cursor.getString(cursor.getColumnIndex(
                    DBHelper.COLUMN_JSON
            ));
            OneDay oneDay = JSON.parseObject(json, OneDay.class);
            oneDays.add(oneDay);
        }
        return oneDays;
    }


    /**
     * 根据日期异步地从数据库中获取OneDay
     *
     * @param date
     * @param callback
     */
    public void findOneDayFromDB(String date, final DBFindCallback callback) {
        SimpleDateFormat sdf = new SimpleDateFormat(OneDay.DATE_FORMAT);
        final String dateStr = sdf.format(date);
        final List<OneDay> days = null;
        DBFindTask task = new DBFindTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                Cursor cursor = OneDayDao.this.db.query(
                        OneDay.ONEDAY_CLASS,
                        null,
                        DBHelper.COLUMN_COMP,
                        new String[]{dateStr},
                        null, null, null);
                if (cursor.moveToNext()) {
                    cursor.move(0);
                    String json = cursor.getString(cursor.getColumnIndex(
                            DBHelper.COLUMN_JSON
                    ));
                    OneDay oneDay = JSON.parseObject(json, OneDay.class);
                    days.add(oneDay);
                }
                return super.doInBackground(params);
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                callback.done(days);
            }
        };
        task.execute();
    }

    /**
     * 根据日期同步地从本地拿取数据
     *
     * @param date 查询的日期
     * @return 找到的OneDay对象
     */
    public OneDay findOneDayFromDB(String date) {
        final String compStr = date + ":"
                + App.getCurrentBaby().getObjectId();
        String whereClause = DBHelper.COLUMN_COMP + "=?";
        String[] whereArgs = {compStr};
        Cursor cursor = OneDayDao.this.db.query(
                OneDay.ONEDAY_CLASS,
                null,
                whereClause,
                whereArgs,
                null, null, null);
        if (cursor.moveToNext()) {
            cursor.move(0);
            String json = cursor.getString(cursor.getColumnIndex(
                    DBHelper.COLUMN_JSON
            ));
            return JSON.parseObject(json, OneDay.class);
        }
        cursor.close();
        return null;
    }

    /**
     * 更新或者储存当天记录，无则更新，有则创建
     *
     * @param src 传入的当天记录
     */
    public void updateOrSaveInDB(Base src) {
        OneDay oneDay = (OneDay) src;
        String date = oneDay.getDate();
        final String compStr = date + ":"
                + App.getCurrentBaby().getObjectId();
        String whereClause = DBHelper.COLUMN_COMP + "=?";
        String[] whereArgs = {compStr};
        String oneDayJSON= JSONHelper.getValidJSON(oneDay.toJSONObject().toString());
        Log.d("oneDayJSON",oneDayJSON);
        OneDay old = findOneDayFromDB(date);
        // 如果已存在且版本不一致,则更新
        if (old != null) {
            String oldJSON=JSONHelper.getValidJSON(old.toJSONObject().toString());
            merge(oneDay, old);
            //保存跟新当前版本标识
            ContentValues cv = new ContentValues();
            cv.put(DBHelper.COLUMN_JSON, oldJSON);
            cv.put(DBHelper.COLUMN_VERSION, old.getVersion());
            db.update(OneDay.ONEDAY_CLASS, cv, whereClause, whereArgs);
        } else {
            //否则直接插入
            dbHelper.insertToJson(
                    OneDay.ONEDAY_CLASS,
                    oneDayJSON,
                    oneDay.getVersion(),
                    compStr
            );
        }
    }


    public void findOneDayFromCloud(Date date, FindCallback callback) {

    }


    /**
     * 与云端同步数据：
     * 1. 先从云端抓取到所有数据
     * 2. 比较日期：
     * 2.1
     * 对于都存在同一日期，而version不相同者，合并更新
     * 2.2
     * 本地存在某一OneDay记录，而云端不存在，更新至云
     * 2.3
     * 本地存在某一OneDay记录，而云端不存在，更新至本地
     * 3. 打包上传
     */
    public void syncCloud() throws AVException {
        query.clearCachedResult();
        //从云端抓取所有
        List<OneDay> daysInCloud = findAllFromCloud(0);
        //从本地抓取所有
        List<OneDay> daysInDB = findAllFromDB(0);
        //否则，比较更新
        for (int i = 0; i < daysInCloud.size(); i++) {
            OneDay cloud = daysInCloud.get(i);
            String cloudDate = cloud.getDate();
            String cloudVersion = cloud.getVersion();
            //遍历搜寻本地的
            for (int j = 0; j < daysInDB.size(); j++) {
                OneDay db = daysInDB.get(i);
                String dbDate = db.getDate();
                String dbVersion = db.getVersion();
                //同一天的不同版本需要更新（云端，本地同时需要更新）
                if (dbDate.equals(cloudDate) &&
                        !dbVersion.equals(cloudVersion)) {
                    //保证有效存储
                    db.setObjectId(cloud.getObjectId());
                    //云端并入本地
                    updateOrSaveInDB(cloud);
                    //本地并入云端
                    updateOrSaveInCloud(db);
                    //删去两条记录,防止重复遍历
                    daysInDB.remove(db);
                    daysInCloud.remove(cloud);
                }
            }
        }
        //如果云端有剩余，更新到本地
        for (OneDay dayInCloud : daysInCloud) {
            updateOrSaveInDB(dayInCloud);
        }
        //如果本地有剩余，更新到云端
        for (OneDay dayInDB : daysInDB) {
            updateOrSaveInCloud(dayInDB);
        }
    }

    /**
     * 合并两个同日记录
     *
     * @param src 合并对象1
     * @param dst 合并对象2，其中2也作为输出合并的对象
     */
    private void merge(OneDay src, OneDay dst) {
        //进行版本比较后合并
        SimpleDateFormat sdf = new SimpleDateFormat(OneDay.VERSION_FORMAT);
        //以较新版本作为新版本
        String version;
        Date dstDate = null, srcDate = null;
        try {
            dstDate = sdf.parse(dst.getVersion());
            srcDate = sdf.parse(src.getVersion());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert dstDate != null;
        version = dstDate.after(srcDate) ?
                dst.getVersion() : src.getVersion();
        List<Record> records = new ArrayList<>();
        List<Record> dstRecords = dst.getRecords();
        List<Record> srcRecords = src.getRecords();
        Log.d("src size is:", String.valueOf(src.getRecords().size()));
        //如果新版本已经包含旧的版本，则覆盖合并
        int count = dstRecords.size() > srcRecords.size() ?
             srcRecords.size() : dstRecords.size();
        int i;
        if (dstRecords.get(0).equals(srcRecords.get(0))) {
             for (i = 0; i < count; i++) {
                  Record dstRecord = dstRecords.get(i);
                  Record srcRecord = srcRecords.get(i);
                  if (!dstRecord.equals(srcRecord)) {
                      records.add(i, dstRecord);
                  } else {
                      SimpleDateFormat compSdf = new SimpleDateFormat("HH:mm:ss");
                      try {
                           Date srcBeginTime = compSdf.parse(srcRecord.getBeginTime());
                           Date dstBeginTime = compSdf.parse(dstRecord.getBeginTime());
                           //将日期较晚的喝奶记录追加在后面
                           if (srcBeginTime.after(dstBeginTime)) {
                           records.add(i + 1, srcRecord);
                           records.add(i, dstRecord);
                           } else {
                               records.add(i + 1, dstRecord);
                               records.add(i, srcRecord);
                           }

                      } catch (ParseException e) {
                           e.printStackTrace();
                      }
                    }
                  }
                  //剩余追加
                  if (dstRecords.size() > count) {
                      records.addAll(dstRecords.subList(i, dstRecords.size()));
                  } else if (srcRecords.size() > count) {
                      records.addAll(srcRecords.subList(i, srcRecords.size()));
                  }
                } else {
                //否则链接合并
                //dst端的版本较新
            if (dstDate.after(srcDate)) {
                records = srcRecords;
                records.addAll(dstRecords);
            }//更新目标的版本较新
            else {
                records = dstRecords;
                records.addAll(srcRecords);
            }
        }

        if (records.size() == 0) {
            return;
        }


        //合并后刷新版本
        dst.setVersion(version);
        dst.setRecords(records);
        //更新分数，奶量
        dst.setScore(src.getScore() + dst.getScore());
        dst.setVolume(Calculator.calcVolume(records));
    }

}
