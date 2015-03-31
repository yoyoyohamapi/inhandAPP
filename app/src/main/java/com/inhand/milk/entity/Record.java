package com.inhand.milk.entity;


import com.alibaba.fastjson.JSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Record
 * Desc: 某次的记录
 * Team: InHand
 * User: Wooxxx
 * Date: 2015-03-04
 * Time: 20:22
 */
//@AVClassName(Record.RECORED_CLASS)
public class Record {
    public static final String RECORED_CLASS = "Milk_Record";

    //奶量
    public static final String VOLUME_KEY = "volume";
    //温度(最低温度，最高温度)
    public static final String BEGIN_TEMPERATURE_KEY = "begin_temperature";
    public static final String END_TEMPERATURE_KEY = "end_temperature";
    //喝奶时间
    public static final String BEGIN_TIME_KEY = "beginTime";
    public static final String END_TIME_KEY = "endTime";
    //关键点
    public static final String KEYPOINTS_KEY="key_points";
    //奶量
    private int volume;
    //温度(最低温度，最高温度)
    private double beginTemperature;
    private double endTemperature;
    //喝奶时间
    private String beginTime;
    private String endTime;
    //关键点序列化
    private String keyPointsJSON;
    public Record() {

    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public double getEndTemperature() {
        return endTemperature;
    }

    public void setEndTemperature(double endTemperature) {
        this.endTemperature = endTemperature;
    }

    public double getBeginTemperature() {
        return beginTemperature;
    }

    public void setBeginTemperature(double beginTemperature) {
        this.beginTemperature = beginTemperature;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<KeyPoint> getKeyPoints() {
        return JSON.parseArray(this.getKeyPointsJSON(), KeyPoint.class);
    }
    public String getKeyPointsJSON(){
        return keyPointsJSON;
    }
    public void setKeyPointsJSON(List<KeyPoint> keyPoints) {
        JSONArray array = new JSONArray();
        for (KeyPoint keyPoint : keyPoints) {
            JSONObject obj = new JSONObject();
            try {
                obj.put(keyPoint.SCALE_KEY,keyPoint.getScale());
                obj.put(keyPoint.TEMPERATURE_KEY,keyPoint.getTemperature());
                obj.put(keyPoint.TIME_KEY,keyPoint.getTime());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(obj);
        }
        this.keyPointsJSON=array.toString();
    }

    /**
     * 根据饮奶的开始时间判断两次饮奶记录是否为
     * 同一条
     *
     * @param record
     * @return
     */
    public boolean equals(Record record) {
        if (record.getBeginTime()
                .equals(this.getBeginTime()))
            return true;
        return false;
    }

    //
//
//    public int getVolume() {
//        return this.getInt(VOLUME_KEY);
//    }
//
//    public void setVolume(int volume) {
//        this.put(VOLUME_KEY,volume);
//    }
//
//    public double getEndTemperature() {
//        return this.getDouble(END_TEMPERATURE_KEY);
//    }
//
//    public void setEndTemperature(double endTemperature) {
//        this.put(END_TEMPERATURE_KEY,endTemperature);
//    }
//
//    public double getBeginTemperature() {
//        return this.getDouble(BEGIN_TEMPERATURE_KEY);
//    }
//
//    public void setBeginTemperature(double beginTemperature) {
//        this.put(BEGIN_TEMPERATURE_KEY,beginTemperature);
//    }
//
//    public Date getBeginTime() {
//        return this.getDate(BEGIN_TIME_KEY);
//    }
//
//    public void setBeginTime(Date beginTime) {
//        this.put(BEGIN_TIME_KEY,beginTime);
//    }
//
//    public Date getEndTime() {
//        return this.getDate(END_TIME_KEY);
//    }
//
//    public void setEndTime(Date endTime) {
//        this.put(END_TIME_KEY,endTime);
//    }
}
