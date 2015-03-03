package milk.inhand.com.inhandmilk.helper;

import android.app.Activity;
import android.content.Context;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;

/**
 * LeanCloudHelper
 * Desc:LeanCloud帮助类
 * Team: InHand
 * User:Wooxxx
 * Date: 2015-03-03
 * Time: 12:49
 */
public class LeanCloudHelper {
    public static  void initLeanCloud(Context ctx){
        AVOSCloud.initialize(ctx, "hfmv1q7runm51bzbgkeu2brt5e2waawmr0519cag9g7dxxf2", "rfhukesd0eyobas5h00aeuzholiczwepkyios3q75jixoawh");
        AVAnalytics.trackAppOpened( ((Activity)ctx).getIntent() );
        AVOSCloud.setDebugLogEnabled(true);
    }
}
