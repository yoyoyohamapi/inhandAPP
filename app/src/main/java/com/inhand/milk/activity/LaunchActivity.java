package com.inhand.milk.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.inhand.milk.App;
import com.inhand.milk.R;

/**
 * LaunchActivity
 * Desc:启动Activity
 * Team: InHand
 * User:Wooxxx
 * Date: 2015-03-19
 * Time: 09:25
 */
public class LaunchActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Handler x = new Handler();
        x.postDelayed(new LaunchHandler(), 2000);
    }

    class LaunchHandler implements Runnable {
        public void run() {
            //如果用户已经登录
            if (((App) getApplication()).logged())
                startActivity(new Intent(getApplication(), SyncTestActivity.class));
            else
                startActivity(new Intent(getApplication(), LogRegActivity.class));
        }

    }
}
