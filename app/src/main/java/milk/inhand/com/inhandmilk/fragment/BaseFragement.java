package milk.inhand.com.inhandmilk.fragment;

import android.app.Fragment;
import android.os.Bundle;

/**
 * BaseFragement
 * Desc:Fragment基类，用于做一些初始化操作
 * Team: InHand
 * User:Wooxxx
 * Date: 2015-03-03
 * Time: 16:54
 */
public class BaseFragement extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        setListeners();
    }

    /**
     * 初始化各个组件
     */
    public void initViews(){

    }


    /**
     * 设置部分组件的监听
     */
    public void setListeners(){

    }
}
