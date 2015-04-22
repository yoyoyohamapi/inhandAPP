package com.inhand.milk.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.inhand.milk.App;
import com.inhand.milk.R;
import com.inhand.milk.entity.FooterItem;

import java.util.List;

/**
 * FooterFragment
 * Desc:底部操作栏Fragment
 * Team: InHand
 * User:Wooxxx
 * Date: 2015-03-13
 * Time: 08:57
 */
public class FooterFragment extends BaseFragment {
    private LinearLayout footer;
    private FragmentManager fm;

    //监听底部栏切换
    class SwitchListener implements View.OnClickListener {
        private Fragment dst;

        SwitchListener(Fragment dst) {
            this.dst = dst;
        }

        @Override
        public void onClick(View v) {
            FragmentTransaction ft =
                    fm.beginTransaction();
            //ft.replace(R.id.main_fragment, dst, Fragment.class.toString());
            ft.commit();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(
                R.layout.fragment_footer,
                container,
                false
        );
        footer = (LinearLayout) rootView.findViewById(R.id.footer_container);
        fm = getFragmentManager();
        initMenus();
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    /**
     * 初始化底部菜单
     */
    private void initMenus() {
        Context ctx = getActivity();
        List<FooterItem> footerItems = ((App) getActivity().getApplication()).getFooterItems();
        for (int i = 0; i < footerItems.size(); i++) {
            FooterItem item = footerItems.get(i);
            int id = getResources()
                    .getIdentifier(item.getIcon(), "drawable", ctx.getPackageName());
            //根据屏幕宽设置选项宽度
            int length = ctx.getResources().getDimensionPixelSize(R.dimen.footer_icon_size);
            LinearLayout layout = new LinearLayout(ctx);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER
            );
            layout.setLayoutParams(lp);
            int padding = (int) ctx.getResources()
                    .getDimension(R.dimen.footer_icon_padding);
            layout.setPadding(
                    padding,
                    padding,
                    padding,
                    padding
            );
            ImageView button = new ImageView(ctx);
            button.setLayoutParams(new LinearLayout.LayoutParams(
                    length,
                    length,
                    Gravity.CENTER
            ));
            button.setImageResource(id);
            button.setScaleType(ImageView.ScaleType.FIT_CENTER);
            Fragment dst = null;
            try {
                dst = (Fragment) Class.forName(item.getFragment())
                        .newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            button.setOnClickListener(new SwitchListener(dst));
            layout.addView(button);
            footer.addView(layout);
        }
    }

    @Override
    public void initViews() {

    }
}
