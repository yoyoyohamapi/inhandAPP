package com.inhand.milk.entity;

import com.avos.avoscloud.AVClassName;

import java.util.Date;

/**
 * Weight
 * Desc: ����ʵ��
 * Date: 2015/6/2
 * Time: 23:43
 * Created by: Wooxxx
 */
@AVClassName(Weight.WEIGHT_CLASS)
public class Weight extends Base {
    public static final String WEIGHT_CLASS="Milk_Weight";

    public static final String MOON_AGE_KEY="moon_age";
    public static final String WEIGHT_KEY = "weight";

    /**
     * ��ñ�������
     * @return ��������
     */
    public int getMoonAge(){
        return this.getInt(MOON_AGE_KEY);
    }

    public void setMoonAge(int moonAge){
        this.put(MOON_AGE_KEY,moonAge);
    }

    /**
     * ��ñ�������
     * @return ��������
     */
    public int getWeight(){
        return this.getInt(WEIGHT_KEY);
    }

    public void setWeight(int weight){
        this.put(WEIGHT_KEY,weight);
    }

}
