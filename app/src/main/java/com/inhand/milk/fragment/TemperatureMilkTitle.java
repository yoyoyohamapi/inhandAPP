package com.inhand.milk.fragment;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.inhand.milk.R;
import com.inhand.milk.activity.MainActivity;

public class TemperatureMilkTitle extends BaseTitle{

	private View view;
	private int leftIcon;
	private String mTitle;
	
	public  TemperatureMilkTitle(String title) {
		mTitle = title;
		// TODO Auto-generated constructor stub
	}
	
	public View getView(Activity activity) {
		// TODO Auto-generated method stub
		return setView(activity, R.layout.title_temperatrue_milk,
					R.drawable.menu_entry,
					mTitle,  ((MainActivity)activity).getMyOnclickListener());
	}
}
