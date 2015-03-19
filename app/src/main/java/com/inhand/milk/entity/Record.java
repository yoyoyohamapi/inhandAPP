package com.inhand.milk.entity;


import java.util.Date;

/**
 * Record
 * Desc: 某次的记录
 * Team: InHand
 * User: Wooxxx
 * Date: 2015-03-04
 * Time: 20:22
 */
public class Record {
    //奶量
    private int volume;
    //温度(最低温度，最高温度)
    private double beginTemperature;
    private double endTemperature;
    //喝奶时间
    private Date beginTime;
    private Date endTime;

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

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
