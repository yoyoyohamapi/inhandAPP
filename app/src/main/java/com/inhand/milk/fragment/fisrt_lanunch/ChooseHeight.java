package com.inhand.milk.fragment.fisrt_lanunch;

import java.nio.channels.spi.SelectorProvider;

import com.inhand.milk.R;
import com.inhand.milk.utils.firstlanunch.Ruler;

import android.app.Fragment;
import android.app.ListActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseHeight extends FirstLaunchFragment{

	private TextView num,unit;
	private ImageView icon,rulerJt;
	private ScrollView scrollView;
	private LinearLayout rulerContainer;
	private RelativeLayout iconContainer;
	private int spacing;
	private int spacingnum = 2;
	private final int TIME = 1000;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.first_launch_choose_height,null);
		setTitle(getResources().getString(R.string.first_launch_choose_height_info));
		setPre();
		initViews(view);
		return view;
	}

	private void initViews(View view){
		num = (TextView)view.findViewById(R.id.first_launch_height_num_textview);
		unit =(TextView)view.findViewById(R.id.first_launch_height_unit_textview);
		scrollView = (ScrollView)view.findViewById(R.id.first_launch_height_scrollView);
		rulerJt = (ImageView)view.findViewById(R.id.first_launch_height_ruler_jt_ico);
		rulerContainer = (LinearLayout)view.findViewById(R.id.first_launch_height_ruler_container);
		iconContainer = (RelativeLayout)view.findViewById(R.id.first_launch_height_icon_container);
		icon = (ImageView)view.findViewById(R.id.first_launch_height_icon_imageview);
		String sex = getExtraInfo();
		if (sex.equals("boy"))
			icon.setImageDrawable(getResources().getDrawable(R.drawable.first_launch_weight_boy_icon));
		else if (sex.equals("girl"))
			icon.setImageDrawable(getResources().getDrawable(R.drawable.first_launch_weight_girl_icon));
		
		int height = (int) getResources().getDimension(R.dimen.first_lanunch_height_choose_height);
		spacing = height/30;
		Ruler ruler = new Ruler(getActivity().getApplicationContext(),height,
					getResources().getDimension(R.dimen.first_lanunch_height_ruler_width),
					0, 0, 100, spacing, spacingnum, false);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);  
		lp.setMargins((int) -getResources().getDimension(R.dimen.first_lanunch_weight_ruler_margin),0,0,20);
		rulerContainer.addView(ruler,lp);
		
		scrollView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				  
				if (event.getAction() == MotionEvent.ACTION_MOVE) {
			        // ���Լ�����ScrollView�Ĺ����¼�
			        float x = scrollView.getScrollY();
			        x = x/spacing * spacingnum /10f;
			        num.setText( String.format("%.1f",x));
			        setNext();
			    }
				return false;
			}
		});
	}
	
	private void setPre(){
		lanunchBottom.setPreListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				enterPreFragment();
			}
		});
	}
	private void setNext(){
		lanunchBottom.setNextListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				outAnimation();
			}
		});
	}
	@Override
	protected Fragment nextFragment() {
		// TODO Auto-generated method stub
		return new ChooseHead();
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		if (!hidden){
			setTitle(getResources().getString(R.string.first_launch_choose_height_info));
			if (	!num.getText().toString().equals("0.0")	)
				setNext();
			iconContainer.clearAnimation();
			inAnimation();
		}
	}

	@Override
	protected void inAnimation() {
		// TODO Auto-generated method stub
		setPre();
		alphAnimation(num, 1f, TIME);
		alphAnimation(unit, 1f, TIME);
		alphAnimation(rulerJt, 1f, TIME);
		alphAnimation(scrollView, 1f, TIME);
	}

	@Override
	protected void outAnimation() {
		// TODO Auto-generated method stub
		alphAnimation(num, 0f, TIME);
		alphAnimation(unit, 0f, TIME);
		alphAnimation(rulerJt, 0f, TIME);
		alphAnimation(scrollView, 0f, TIME);
		

		int movedright = (int) (width/2 - width*0.6 + icon.getWidth()/2);
		Animation animation = new TranslateAnimation(0,movedright,0,0);
		animation.setFillAfter(true);
		animation.setDuration(TIME);
		iconContainer.startAnimation(animation);
		animation.setAnimationListener( new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				enterNextFragmet();
				save();
			}
		});
	}
	/*
	 * num.getText().toString() 返回身高单位厘米
	 */

    private void save(){
       Float height=Float.valueOf(num.getText().toString());
       baby.setHeight(height);
    }

}
