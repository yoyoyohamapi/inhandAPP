package com.inhand.milk.entity;

/**
 * KeyPoint
 * Desc:蓝牙记录关键点
 * Team: InHand
 * User:Wooxxx
 * Date: 2015-03-18
 * Time: 23:05
 */
public class KeyPoint {
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

    public KeyPoint() {
    }

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
}
