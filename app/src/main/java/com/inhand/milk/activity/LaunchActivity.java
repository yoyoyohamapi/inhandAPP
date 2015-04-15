package com.inhand.milk.activity;

import android.os.Bundle;

import com.inhand.milk.R;
import com.inhand.milk.fragment.LoginOptFragment;

/**
 * LaunchActivity
 * Desc:启动Activity
 * Team: InHand
 * User:Wooxxx
 * Date: 2015-03-19
 * Time: 09:25
 */
public class LaunchActivity extends BaseActivity {
    public static final Class LOGGED_TO = BabyInfoTestActivity.class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, new LoginOptFragment()).commit();

    }


}
