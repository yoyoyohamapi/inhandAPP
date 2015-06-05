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
 * Desc: ͳ����Ϣ���ݷ��ʲ�
 * Date: 2015/6/3
 * Time: 7:24
 * Created by: Wooxxx
 */
public class StatisticsDao extends BabyDao{

    public StatisticsDao(Context ctx) {
        super(ctx);
    }

    /**
     * ͬ���ش��ƶ˻�ȡ��ǰ������ͳ����Ϣ
     */
    public Statistics findFromCloudByCunrrentBaby(){
        return null;
    }

    /**
     * �첽�ش��ƶ˻�ȡ��ǰ������ͳ����Ϣ
     * @param callback
     */
    public void findFromCloudByCurrentBaby(final FindCallback callback){

    }

    /**
     * ͬ���شӱ��ػ�ȡ��ǰ������ͳ����Ϣ
     * @return ��ǰ������ͳ����Ϣ
     */
    public Statistics findFromDBByCurrentBaby(){
        return null;
    }

    /**
     * �첽�شӱ��ػ�ȡ��ǰ������ͳ����Ϣ
     * @param callback �ص��ӿ�
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
