package com.inhand.milk.fragment.fisrt_lanunch;

import java.nio.channels.spi.SelectorProvider;

import com.inhand.milk.R;
import com.inhand.milk.R.layout;
import com.inhand.milk.utils.firstlanunch.Ruler;

import android.R.integer;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.webkit.WebIconDatabase.IconListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseWeight extends FirstLaunchFragment{
	
	private ImageView Icon,rulerJt;
	private LinearLayout ruler;
	private TextView num,unit;
	private HorizontalScrollView ScrollView;
	private static final int time = 1000;
	private int spacing;
	private int spacingnum = 2;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.first_launch_choose_weight, null);
		setTitle(getResources().getString(R.string.first_launch_choose_weight_info));
		initViews(view);
		return view;
	}

	private void initViews(View view){
		Icon = (ImageView)view.findViewById(R.id.first_launch_weight_girl_boy_icon);
		String sex = getExtraInfo();
		if (sex.equals("boy"))
			Icon.setImageDrawable(getResources().getDrawable(R.drawable.first_launch_weight_boy_icon));
		else if (sex.equals("girl"))
			Icon.setImageDrawable(getResources().getDrawable(R.drawable.first_launch_weight_girl_icon));
		
		rulerJt = (ImageView)view.findViewById(R.id.first_launch_choose_head_ruler_jt_icon);
		ruler = (LinearLayout)view.findViewById(R.id.first_launch_choose_head_ruler_container);
		num = (TextView)view.findViewById(R.id.first_launch_weight_num_textview);
		unit = (TextView)view.findViewById(R.id.first_launch_weight_unit_textview);
		ScrollView = (HorizontalScrollView)view.findViewById(R.id.first_launch_choose_head_scrollView);
		
		spacing = (int) width/30;
		Ruler rulerView = new Ruler( getActivity().getApplicationContext(), width*0.75f ,
					0f,(float) getResources().getDimension(R.dimen.first_lanunch_weight_ruler_height),
					0, 500,spacing,spacingnum, true);
		
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);  
		
		lp.setMargins(0, (int) getResources().getDimension(R.dimen.first_lanunch_weight_ruler_margin)
				, 20, 0);
		ruler.addView(rulerView,lp);
		
		
		
		ScrollView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				 Log.i("ruler", String.valueOf( (ScrollView.getScrollX()) ) );
		    
				if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    // 可以监听到ScrollView的滚动事件
			        Log.i("ruler", String.valueOf( (ScrollView.getScrollX()) ) );
			        float x = ScrollView.getScrollX();
			        x = x/spacing * spacingnum /10f;
			        num.setText( String.format("%.1f",x));
			        setNext();
			    }
				return false;
			}
		});
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		if (!hidden){
			setTitle(getResources().getString(R.string.first_launch_choose_weight_info));
			if (	!num.getText().toString().equals("0.0")	)
				setNext();
			Icon.clearAnimation();
			inAnimation();
		}
	}

	@Override
	protected Fragment nextFragment() {
		// TODO Auto-generated method stub
		return new ChooseHeight();
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
	@Override
	protected void inAnimation() {
		// TODO Auto-generated method stub
		setPre();
		alphAnimation(unit, 1f, time);
		alphAnimation(num, 1f, time);
		alphAnimation(ScrollView, 1f, time);
		alphAnimation(rulerJt, 1f, time);
		Animation animation = new TranslateAnimation(-width/2,0, 0, 0);
		animation.setFillAfter(true);
		animation.setDuration(time);
		Icon.startAnimation(animation);
	}

	@Override
	protected void outAnimation() {
		// TODO Auto-generated method stub
		
		alphAnimation(unit, 0f, time);
		alphAnimation(num, 0f, time);
		alphAnimation(ScrollView, 0f, time);
		alphAnimation(rulerJt, 0f, time);
		
		int movedown = (int) getResources().getDimension(
				R.dimen.first_lanunch_weight_ruler_container_container_height);
		Animation animation = new TranslateAnimation(0,0,0,movedown);
		animation.setFillAfter(true);
		animation.setDuration(time);
		Icon.startAnimation(animation);
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
	
	private void setNext(){
		lanunchBottom.setNextListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				outAnimation();
			}
		});
	}
    /*
     * Num. getText 表示 返回体重 单位 kg
     */
	private void save(){
        Float weight=Float.valueOf(num.getText().toString());
        baby.setWeight(weight);
	}
}
