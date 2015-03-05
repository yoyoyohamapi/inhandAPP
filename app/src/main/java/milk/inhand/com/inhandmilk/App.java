package milk.inhand.com.inhandmilk;

import android.app.Application;

import milk.inhand.com.inhandmilk.helper.LeanCloudHelper;

/**
 * App
 * Desc: 应用环境初始化
 * Team: InHand
 * User: Wooxxx
 * Date: 2015-03-05
 * Time: 10:37
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeanCloudHelper.initLeanCloud(this);
    }
}
