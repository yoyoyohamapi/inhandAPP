package com.inhand.milk.dao;

import android.content.Context;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.FindCallback;
import com.inhand.milk.entity.Base;
import com.inhand.milk.entity.Statistics;

import java.util.List;

/**
 * StatisticsDao
 * Desc: 统计信息数据访问层
 * Date: 2015/6/3
 * Time: 7:24
 * Created by: Wooxxx
 */
public class StatisticsDao extends BabyDao{

    public StatisticsDao(Context ctx) {
        super(ctx);
    }

    /**
     * 同步地从云端获取当前宝宝的统计信息
     */
    public Statistics findFromCloudByCunrrentBaby(){
        return null;
    }

    /**
     * 异步地从云端获取当前宝宝的统计信息
     * @param callback
     */
    public void findFromCloudByCurrentBaby(final FindCallback callback){

    }

    /**
     * 同步地从本地获取当前宝宝的统计信息
     * @return 当前宝宝的统计信息
     */
    public Statistics findFromDBByCurrentBaby(){
        return null;
    }

    /**
     * 异步地从本地获取当前宝宝的统计信息
     * @param callback 回调接口
     */
    public void findFromDBByCurrentBaby(
            final DBFindCallback<Statistics> callback){

    }

    @Override
    public void findAllFromCloud(int limit, FindCallback findCallback) {
        super.findAllFromCloud(limit, findCallback);
    }

    @Override
    public <T extends Base> List<T> findAllFromCloud(int limit) {
        return super.findAllFromCloud(limit);
    }

    @Override
    public void updateOrSaveInCloud(Base obj) throws AVException {
        super.updateOrSaveInCloud(obj);
    }

    @Override
    public void updateOrSaveInCloud(Base oneDay, FindCallback callback) {
        super.updateOrSaveInCloud(oneDay, callback);
    }

    @Override
    public <T extends Base> List<T> findAllFromDB(int limit) {
        return super.findAllFromDB(limit);
    }

    @Override
    public void updateOrSaveInDB(Base src) {
        super.updateOrSaveInDB(src);
    }
}
