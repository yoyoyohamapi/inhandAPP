package com.inhand.milk.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.FindCallback;
import com.inhand.milk.entity.Base;
import com.inhand.milk.helper.DBHelper;

import java.util.List;

/**
 * BaseDao
 * Desc:基础Dao类
 * Team: InHand
 * User:Wooxxx
 * Date: 2015-03-04
 * Time: 21:24
 */
public class BaseDao {
    protected DBHelper dbHelper;
    protected SQLiteDatabase db;

    public BaseDao(Context ctx) {
        dbHelper = DBHelper.getInstance(ctx);
        db = dbHelper.openDatabase();
    }

    /**
     * @return
     */
    public AVObject findByObjectId(String objectId) {
        return null;
    }

    /**
     * 异步地从云端获取所有数据
     * @param limit        最大数量，如果为0，代表传入所有
     * @param findCallback 查询完后的回调函数
     */
    public void findAllFromCloud(int limit, FindCallback findCallback) {

    }

    /**
     * 同步地从云端获取所有数据
     *
     * @param limit 最大获取数量，如果为0，代表传入所有
     */
    public <T extends Base> List<T> findAllFromCloud(int limit) {
        return null;
    }


    /**
     * 同步地在云端更新或者储存一个AVObject
     *
     * @param obj 待储存更新对象
     */
    public void updateOrSaveInCloud(Base obj) throws AVException {

    }

    /**
     * 异步地在云端更新或者储存一个AVObject
     *
     * @param oneDay   待储存更新对象
     * @param callback 回调函数
     */
    public void updateOrSaveInCloud(Base oneDay, FindCallback callback) {

    }

    /**
     * 同步的从本地抓取所有
     *
     * @param limit 最大数量，如果为0，代表传入所有
     * @return
     */
    public <T extends Base> List<T> findAllFromDB(int limit) {
        return null;
    }

    /**
     * 同步地更新AVObject至本地数据库
     *
     * @param src 目标更新对象
     */
    public void updateOrSaveInDB(Base src) {
    }

    /**
     * 数据库查询异步任务
     */
    public class DBFindTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
        }
    }

    public interface DBFindCallback<T> {
        public void done(List<T> results);
    }
}
