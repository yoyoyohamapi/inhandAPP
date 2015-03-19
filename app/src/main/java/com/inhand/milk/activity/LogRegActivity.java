package com.inhand.milk.activity;

import android.os.Bundle;

import com.inhand.milk.R;
import com.inhand.milk.fragment.LoginFragment;

/**
 * LogRegActivity
 * Desc:登陆注册Activity
 * Team: InHand
 * User:Wooxxx
 * Date: 2015-03-03
 * Time: 16:52
 */
public class LogRegActivity extends BaseActivity {
    //注册及登录出口
    public static final Class entry = SyncTestActivity.class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_reg);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, new LoginFragment()).commit();
    }
}
