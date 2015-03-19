package com.inhand.milk.entity;


import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.inhand.milk.dao.BabyDao;
import com.inhand.milk.dao.OneDayDao;


/**
 * User
 * Desc:
 * Team: InHand
 * User:Wooxxx
 * Date: 2015-03-19
 * Time: 14:35
 */
public class User extends AVUser {
    //昵称列
    public static final String NICKNAME_KEY = "nickname";

    public String getNickname() {
        return this.getString(NICKNAME_KEY);
    }

    public void setNickname(String nickname) {
        this.put(NICKNAME_KEY, nickname);
    }

    //获得当前用户的baby
    public void getBabies(FindCallback<Baby> findCallback) {
        BabyDao babyDao = new BabyDao();
        babyDao.findBabiesByUser(this, findCallback);
    }

    //获得当前用户的喝奶记录
    public void getOnedays(int limit, FindCallback<OneDay> findCallback) {
        OneDayDao oneDayDao = new OneDayDao();
        oneDayDao.findAllOrLimit(limit, findCallback);
    }
}
