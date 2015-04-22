package com.inhand.milk.entity;


import com.alibaba.fastjson.JSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Record
 * Desc: 某次的记录
 * Team: InHand
 * User: Wooxxx
 * Date: 2015-03-04
 * Time: 20:22
 */
public class Record implements Serializable{
    //奶量
    public static final String VOLUME_KEY = "volume";
    //温度(最低温度，最高温度)
    public static final String BEGIN_TEMPERATURE_KEY = "beginTemperature";
    public static final String END_TEMPERATURE_KEY = "endTemperature";
    //喝奶时间
    public static final String BEGIN_TIME_KEY = "beginTime";
    public static final String END_TIME_KEY = "endTime";
    //关键点
    public static final String KEY_POINTS_KEY = "keyPointsJSON";
    //奶量
    private int volume;
    //温度(最低温度，最高温度)
    private float beginTemperature;
    private float endTemperature;
    //喝奶时间
    private String beginTime;
    private String endTime;
    //关键点序列化
    private JSONArray keyPointsJSON;
    public Record() {

    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public float getEndTemperature() {
        return endTemperature;
    }

    public void setEndTemperature(float endTemperature) {
        this.endTemperature = endTemperature;
    }

    public float getBeginTemperature() {
        return beginTemperature;
    }

    public void setBeginTemperature(float beginTemperature) {
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

    public List<KeyPoint> fetchKeyPoints() {
        return JSON.parseArray(this.getKeyPointsJSON().toString(), KeyPoint.class);
    }
    public List<KeyPoint> getKeyPoints(){
        return JSON.parseArray(this.keyPointsJSON.toString(), KeyPoint.class);
    }
    public JSONArray getKeyPointsJSON() {
        return keyPointsJSON;
    }
    public void setKeyPointsJSON(List<KeyPoint> keyPoints) {
        JSONArray array = new JSONArray();
        for (KeyPoint keyPoint : keyPoints) {
            JSONObject obj = new JSONObject();
            try {
                obj.put(KeyPoint.SCALE_KEY, keyPoint.getScale());
                obj.put(KeyPoint.TEMPERATURE_KEY, keyPoint.getTemperature());
                obj.put(KeyPoint.TIME_KEY, keyPoint.getTime());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(obj);
        }
        this.keyPointsJSON = array;
    }


    /**
     * 根据饮奶的开始时间判断两次饮奶记录是否为
     * 同一条
     *
     * @param record 比较对象
     * @return 是否相等
     */
    public boolean equals(Record record) {
        if (record.getBeginTime()
                .equals(this.getBeginTime()))
            return true;
        return false;
    }
}
