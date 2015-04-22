package com.inhand.milk.fragment;


import java.util.zip.Inflater;

import android.R.integer;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.inhand.milk.R;
import com.inhand.milk.activity.MainActivity;

public class MainTitle extends BaseTitle{

	private String mTitle;
	public MainTitle( String title) {
		mTitle = title;
		
	}
	public View getView(Activity activity) {
		// TODO Auto-generated method stu
		return setView(activity, R.layout.title_main,
				R.drawable.menu_entry,mTitle,
				((MainActivity)activity).getMyOnclickListener() );
	}
	
}
