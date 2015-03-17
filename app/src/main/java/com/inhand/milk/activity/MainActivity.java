package com.inhand.milk.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.inhand.milk.R;
import com.inhand.milk.fragment.FooterFragment;
import com.inhand.milk.fragment.HeaderFragment;
import com.inhand.milk.fragment.MainFragment;

/**
 * MainActivity
 * Desc:
 * Team: InHand
 * User:Wooxxx
 * Date: 2015-03-13
 * Time: 10:42
 */
public class MainActivity extends BaseActivity {
    private HeaderFragment header;
    private MainFragment main;
    private FooterFragment footer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();
//        fm.beginTransaction()
//                .replace(R.id.header_fragment, new HeaderFragment(),"Header")
//                .replace(R.id.main_fragment, new VolumeFragment(),"Main")
//                .replace(R.id.footer_fragment,new FooterFragment(),"Footer")
//                .commit();

        header = (HeaderFragment) fm.findFragmentById(R.id.header_fragment);
        main = (MainFragment) fm.findFragmentById(R.id.main_fragment);

    }


    public HeaderFragment getHeader() {
        return header;
    }

    public MainFragment getMain() {
        return main;
    }

    public FooterFragment getFooter() {
        return footer;
    }
}
