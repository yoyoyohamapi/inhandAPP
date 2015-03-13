package com.inhand.milk;

import android.app.Application;

import com.inhand.milk.helper.LeanCloudHelper;

/**
 * App
 * Desc: 应用环境初始化
 * Team: InHand
 * User: Wooxxx
 * Date: 2015-03-05
 * Time: 10:37
 */
public class App extends Application {
    public static final String FOOTER_CONFIG = "config/footer_items.json";
    @Override
    public void onCreate() {
        super.onCreate();
        LeanCloudHelper.initLeanCloud(this);
    }
}
