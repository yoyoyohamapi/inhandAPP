package com.inhand.milk.entity;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;

/**
 * KeyPoint
 * Desc:蓝牙记录关键点
 * Team: InHand
 * User:Wooxxx
 * Date: 2015-03-18
 * Time: 23:05
 */
@AVClassName(KeyPoint.KEYPOINT_CLASS)
public class KeyPoint extends AVObject {
    public static final String KEYPOINT_CLASS = "Milk_KeyPoint";
    //刻度
    public static final String SCALE_KEY = "scale";
    //时间
    public static final String TIME_KEY = "time";
    public static final String USER_RELATION_KEY = "key_point";

    public int getScale() {
        return this.getInt(SCALE_KEY);
    }

    public void setScale(int scale) {
        this.put(SCALE_KEY, scale);
    }

    public String getTime() {
        return this.getString(TIME_KEY);
    }

    public void setTime(int time) {
        this.put(TIME_KEY, time);
    }

    /**
     * 存储KeyPoint对象，若该“日”已存在，则为更新
     */
    public void save(final SaveCallback saveCallback) {
        this.saveInBackground(saveCallback);
        //关联用户
        AVUser user = AVUser.getCurrentUser();
        AVRelation<AVObject> relation = user.getRelation(USER_RELATION_KEY);
        relation.add(this);
        user.saveInBackground(saveCallback);
    }


}
