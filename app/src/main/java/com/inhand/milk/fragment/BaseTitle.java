package com.inhand.milk.fragment;

import com.inhand.milk.R;
import com.inhand.milk.activity.MainActivity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BaseTitle {

	protected  View setView(Activity activity,int layoutId,int iconLeft ,String title,
			OnClickListener listener) {
		LayoutInflater inflater = activity.getLayoutInflater();
		View view = inflater.inflate(layoutId, null);
		
		ImageView leftIcon = (ImageView) view.findViewById(R.id.title_left_icon);
		leftIcon.setOnClickListener(  listener);
		leftIcon.setBackgroundResource(iconLeft);
		
		((TextView)view.findViewById(R.id.title_text) ).setText(title);
		return view;
	}
}
