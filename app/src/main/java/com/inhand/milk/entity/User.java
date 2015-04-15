package com.inhand.milk.entity;


import android.content.Context;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.inhand.milk.dao.BabyDao;


/**
 * User
 * Desc:
 * Team: InHand
 * User:Wooxxx
 * Date: 2015-03-19
 * Time: 14:35
 */
public class User extends AVUser {
    public static final String CLASS_NAME = "_USER";
    //昵称列
    public static final String NICKNAME_KEY = "nickname";

    public String getNickname() {
        return this.getString(NICKNAME_KEY);
    }

    public void setNickname(String nickname) {
        this.put(NICKNAME_KEY, nickname);
    }


    /**
     * 取得当前用户的所有宝宝
     *
     * @param ctx          上下文环境
     * @param findCallback 回调接口
     */
    public void getBabies(Context ctx, FindCallback<Baby> findCallback) {
        BabyDao babyDao = new BabyDao(ctx);
        babyDao.findBabiesByUser(this, findCallback);
    }


}
