package com.inhand.milk;

import android.app.Application;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVUser;
import com.inhand.milk.entity.Baby;
import com.inhand.milk.entity.User;
import com.inhand.milk.helper.LeanCloudHelper;
import com.inhand.milk.utils.ACache;

/**
 * App
 * Desc: 应用环境初始化
 * Team: InHand
 * User: Wooxxx
 * Date: 2015-03-05
 * Time: 10:37
 */
public class App extends Application {
    public static final String BABY_CACHE_KEY = "current_baby";
    public static Baby currentBaby = null;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化LeanCloud
        LeanCloudHelper.initLeanCloud(this);
        initCurrentBaby();
    }



    public static User getCurrentUser() {
        return AVUser.cast(AVUser.getCurrentUser(), User.class);
    }

    public void initCurrentBaby() {
        if (currentBaby == null) {
            ACache aCache = ACache.get(this);
            String json = aCache.getAsString(BABY_CACHE_KEY);
            currentBaby = JSON.parseObject(json, Baby.class);
        }
    }

    /**
     * 获得当前宝宝
     *
     * @return 当前宝宝
     */
    public static Baby getCurrentBaby() {
        return currentBaby;
    }

    /**
     * 判断用户是否登陆
     * @return 登陆与否
     *
     *
     */
    public static boolean logged() {
        if (AVUser.getCurrentUser() == null)
            return false;
        return true;
    }

}
