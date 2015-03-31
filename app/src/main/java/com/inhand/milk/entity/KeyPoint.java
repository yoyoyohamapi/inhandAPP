package com.inhand.milk.entity;

import android.content.Context;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.inhand.milk.dao.OneDayDao;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * KeyPoint
 * Desc:蓝牙记录关键点
 * Team: InHand
 * User:Wooxxx
 * Date: 2015-03-18
 * Time: 23:05
 */
public class KeyPoint{    public static final String KEYPOINT_CLASS = "Milk_KeyPoint";
    //刻度
    public static final String SCALE_KEY = "scale";
    //时间
    public static final String TIME_KEY = "time";
    //温度
    public static  final  String TEMPERATURE_KEY="temperature";

    //刻度
    private int scale;
    //温度
    private double temperature;
    //时间
    private int time;

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
    //    public int getScale() {
//        return this.getInt(SCALE_KEY);
//    }
//
//    public void setScale(int scale) {
//        this.put(SCALE_KEY, scale);
//    }
//
//    public String getTime() {
//
//        return this.getString(TIME_KEY);
//    }
//
//    public void setTime(int time) {
//        this.put(TIME_KEY, time);
//    }
//
//    public double getTemperature() {
//        return this.getDouble(TEMPERATURE_KEY);
//    }
//
//    public void setTemperature(double temperature) {
//        this.put(TEMPERATURE_KEY, temperature);
//    }
//    public  void setVersion(String version){
//
//        this.put(VERSION_KEY,version);
//    }
//    public String getVersion(){
//        return this.getString(VERSION_KEY);
//    }
//    /**
//     * 将数据存储至云端
//     * 存储OneDay对象，若该“日”已存在，则为更新
//     */
//    public void save(Context ctx, final SaveCallback saveCallback) {
//        final KeyPoint keyPoint = this;
//        //更新版本表示
//        String version = keyPoint.getVersion();
//        if (version == null) {
//            //如果先前没有手动设置过version
//            SimpleDateFormat sdf = new SimpleDateFormat(VERSION_FORMAT);
//            version = sdf.format(new Date());
//        }
//        keyPoint.setVersion(version);
//        KeyPointDao keyPointDao = new KeyPointDao(ctx);
//        keyPointDao.findKeyPointFromCloud(keyPoint, new FindCallback<OneDay>() {
//            @Override
//            public void done(List<OneDay> oneDays, AVException e) {
//                //如果不存在，插入
//                if (e != null) {
//                    day.setBaby(App.getCurrentBaby());
//                    day.saveInBackground(saveCallback);
//                } else {
//                    //否则更新
//                    OneDay oneDay = oneDays.get(0);
//                    oneDay.setRecords(day.getRecords());
//                    oneDay.setVolume(day.getVolume());
//                    oneDay.setScore(day.getScore());
//                    oneDay.setVersion(day.getVersion());
//                    oneDay.saveInBackground(saveCallback);
//                }
//            }
//        });
//    }
//
//    /**
//     * 将数据存至数据库
//     *
//     * @param ctx      上下文环境
//     * @param callback 回调接口
//     */
//    public void saveInDB(final Context ctx,
//                         final Base.DBSavingCallback callback) {
//        //更新版本表示
//        String version = this.getVersion();
//        if (version == null) {
//            //如果先前没有手动设置过version
//            SimpleDateFormat sdf = new SimpleDateFormat(VERSION_FORMAT);
//            version = sdf.format(new Date());
//        }
//        this.setVersion(version);
//        DBSavingTask task = new DBSavingTask(ctx, callback) {
//            @Override
//            protected Object doInBackground(Object[] params) {
//                //先查询数据库中是否有此记录
//                KeyPointDao keyPointDao = new KeyPointDao(ctx);
//                keyPointDao.updateOrSaveInDB(KeyPoint.this);
//                return null;
//            }
//        };
//        task.execute();
//    }
//    /**
//     * 存储KeyPoint对象，若该“日”已存在，则为更新
//     */
//    public void save(final SaveCallback saveCallback) {
//        this.saveInBackground(saveCallback);
//        //关联用户
//        AVUser user = AVUser.getCurrentUser();
//        AVRelation<AVObject> relation = user.getRelation(USER_RELATION_KEY);
//        relation.add(this);
//        user.saveInBackground(saveCallback);
//    }


}
