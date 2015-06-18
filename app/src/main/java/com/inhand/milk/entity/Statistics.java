package com.inhand.milk.entity;

import android.content.Context;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.inhand.milk.App;
import com.inhand.milk.dao.StatisticsDao;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Statistics
 * Desc: ͳ��ʵ��
 * Date: 2015/6/2
 * Time: 23:42
 * Created by: Wooxxx
 */
@AVClassName(Statistics.STATISTICS_CLASS)
public class Statistics extends Base {
    public static final String STATISTICS_CLASS = "Milk_Statistics";
    public static final String OVERTIME_NUM_KEY = "overtime_num";
    public static final String HIGH_TEMPERATURE_NUM_KEY = "high_temperature_num";
    public static final String LOW_TEMPERATURE_NUM_KEY = "low_temperature_num";
    public static final String BABY_KEY = "baby";
    public static final String VERSION_KEY = "version";

    public Statistics(){

    }

    /**
     * ������̳�ʱ����
     * @return ���̳�ʱ����
     */
    public int getOvertimeNum(){
        return this.getInt(OVERTIME_NUM_KEY);
    }

    public void setOvertimeNum(int overtimeNum){
        this.put(OVERTIME_NUM_KEY,overtimeNum);
    }

    /**
     * ��������¶ȹ��ߴ���
     * @return �����¶ȹ��ߴ���
     */
    public int getHighTemperatureNum(){
        return this.getInt(HIGH_TEMPERATURE_NUM_KEY);
    }

    public void setHighTemperatureNum(int highTemperatureNum){
        this.put(HIGH_TEMPERATURE_NUM_KEY,highTemperatureNum);
    }

    /**
     * ��������¶ȹ��ʹ���
     * @return �����¶ȹ��ʹ���
     */
    public int getLowTemperatureNum(){
        return this.getInt(LOW_TEMPERATURE_NUM_KEY);
    }

    public void setLowTemperatureNum(int lowTemperatureNum){
        this.put(LOW_TEMPERATURE_NUM_KEY,lowTemperatureNum);
    }

    public String getVersion(){
        return this.getString(VERSION_KEY);
    }

    public void setVersion(String version){
        this.put(VERSION_KEY,version);
    }

    public Baby getBaby(){
        try {
            return this.getAVObject(BABY_KEY,Baby.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * ����ͳ����Ϣ��Ӧ�ı���
     * @param baby ����
     */
    public void setBaby(Baby baby) {
        try {
            this.put(BABY_KEY, AVObject.createWithoutData(Baby.class, baby.getObjectId()));
        } catch (AVException e) {
            e.printStackTrace();
        }
    }

    /**
     * ����ͳ����Ϣ���ƶ�
     * @param callback �洢�ص��ӿ�
     * @param ctx �����Ļ���
     */
    public void saveInCloud(Context ctx,final SaveCallback callback){
        if( this.getBaby()==null){
            this.setBaby(App.getCurrentBaby());
        }
        this.saveInBackground(callback);
    }

    /**
     * ����ͳ����Ϣ������
     * @param ctx �����Ļ���
     * @param callback �洢�ص��ӿ�
     */
    public void saveInDB(final Context ctx,final DBSavingCallback callback){
        if( this.getBaby()==null){
            this.setBaby(App.getCurrentBaby());
        }
        //���°汾��ʾ
        SimpleDateFormat sdf = new SimpleDateFormat(VERSION_FORMAT);
        String version = sdf.format(new Date());
        this.setVersion(version);
        DBSavingTask task = new DBSavingTask(ctx, callback) {
            @Override
            protected Object doInBackground(Object[] params) {
                //���±������ݿ�
                StatisticsDao statisticsDao = new StatisticsDao(ctx);
                statisticsDao.updateOrSaveInDB(Statistics.this);
                return null;
            }
        };
        task.execute();
    }

}
