package com.inhand.milk;

import android.app.Application;

import com.inhand.milk.entity.FooterItem;
import com.inhand.milk.entity.SlidingItem;
import com.inhand.milk.helper.JSONHelper;
import com.inhand.milk.helper.LeanCloudHelper;

import java.util.List;

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
    public static final String SLIDING_CONFIG = "config/sliding_items.json";
    private List<FooterItem> footerItems = null;
    private List<SlidingItem> slidingItems = null;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化LeanCloud
        LeanCloudHelper.initLeanCloud(this);
        //初始化底部菜单项
        slidingItems = JSONHelper.parseJSONArray(
                this,
                App.SLIDING_CONFIG,
                SlidingItem.class
        );
        //初始化侧边栏
        footerItems = JSONHelper.parseJSONArray(
                this,
                App.FOOTER_CONFIG,
                FooterItem.class
        );
    }

    public List<FooterItem> getFooterItems() {
        return footerItems;
    }

    public List<SlidingItem> getSlidingItems() {
        return slidingItems;
    }
}
