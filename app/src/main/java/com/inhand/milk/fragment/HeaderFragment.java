package com.inhand.milk.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.inhand.milk.App;
import com.inhand.milk.R;
import com.inhand.milk.entity.SlidingItem;
import com.inhand.milk.utils.ViewHolder;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.List;

/**
 * HeaderFragment
 * Desc:顶部栏Fragment
 * Team: InHand
 * User:Wooxxx
 * Date: 2015-03-14
 * Time: 13:05
 */
public class HeaderFragment extends BaseFragment {
    private SlidingMenu slidingMenu;
    private ListView menuList;
    private ImageView slidingEntry;
    private List<SlidingItem> slidingItems;
    private SlidingAdapter adapter;
    private TextView titleTxt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_header, container, false);
        //如果为android 5.0,应用沉浸式状态栏
        enableImmersiveBar();
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initSlidingMenu();
        //获得地理位置

    }

    /**
     * 初始化侧滑菜单
     */
    private void initSlidingMenu() {
        //        SlidingMenu 基本配置
        slidingMenu = new SlidingMenu(getActivity());
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        slidingMenu.setBehindOffsetRes(R.dimen.sliding_behindOffset);
        slidingMenu.setShadowWidth(10);
        slidingMenu.setDrawingCacheEnabled(true);
        slidingMenu.setMenu(R.layout.slidingmenu);
        slidingMenu.attachToActivity(getActivity(), SlidingMenu.SLIDING_CONTENT);
        menuList = (ListView) slidingMenu.findViewById(R.id.list);
        initMenuList();
    }


    /**
     * 初始化侧滑菜单列表
     */
    private void initMenuList() {
        Context ctx = getActivity();
        slidingItems = ((App) getActivity().getApplication()).getSlidingItems();
        adapter = new SlidingAdapter();
        menuList.setAdapter(adapter);
    }

    @Override
    public void setListeners() {
        slidingEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });
    }

    @Override
    public void initViews() {
        slidingEntry = (ImageView) rootView.findViewById(R.id.sliding_entry);
        titleTxt = (TextView) rootView.findViewById(R.id.title);
    }

    /**
     * 是否允许沉浸式状态栏
     */
    private void enableImmersiveBar() {
        int version = Build.VERSION.SDK_INT;
        if (version >= 21)
            getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.default_bkg));
    }

    /**
     * 为SlidingMenu中的菜单列表适配
     */
    public class SlidingAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return slidingItems.size();
        }

        @Override
        public SlidingItem getItem(int position) {
            return slidingItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            SlidingItem item = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity())
                        .inflate(R.layout.item_sliding, parent, false);
            }
            int imgId = getResources()
                    .getIdentifier(item.getIcon(), "drawable", getActivity().getPackageName());
            ImageView iconImg = ViewHolder.get(convertView, R.id.icon);
            int nameId = getResources()
                    .getIdentifier(item.getName(), "string", getActivity().getPackageName());
            TextView nameTxt = ViewHolder.get(convertView, R.id.name);
            iconImg.setBackgroundResource(imgId);
            nameTxt.setText(getString(nameId));
            return convertView;
        }
    }

    //设置标题
    public void setTitle(String title) {
        titleTxt.setText(title);
    }

}
