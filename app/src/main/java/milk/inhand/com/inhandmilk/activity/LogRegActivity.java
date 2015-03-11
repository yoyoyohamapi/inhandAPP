package milk.inhand.com.inhandmilk.activity;

import android.os.Bundle;

import milk.inhand.com.inhandmilk.R;
import milk.inhand.com.inhandmilk.fragment.LoginFragment;

/**
 * LogRegActivity
 * Desc:登陆注册Activity
 * Team: InHand
 * User:Wooxxx
 * Date: 2015-03-03
 * Time: 16:52
 */
public class LogRegActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_reg);
        getFragmentManager().beginTransaction()
                .replace(R.id.main_container, new LoginFragment()).commit();
    }
}
