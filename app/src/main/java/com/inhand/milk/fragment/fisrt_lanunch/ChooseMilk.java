package com.inhand.milk.fragment.fisrt_lanunch;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.inhand.milk.R;
import com.inhand.milk.activity.MainActivity;
import com.inhand.milk.entity.Base;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseMilk extends FirstLaunchFragment {

	private TextView text,doc;
	private ImageView brandLeft,brandRight;
	private static final int TIME = 1000;
    private int selectMilk;
    public static int green_milk=0;
    public static int yellow_milk=1;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.first_launch_choose_milk, null);
		setTitle(getResources().getString(R.string.first_launch_choose_milk_info));
		initView(view);
		setPre();
		setNext();
		return view;
	}
	
	private void initView(View view){
		text = (TextView)view.findViewById(R.id.first_launch_choose_milk_text);
		doc = (TextView)view.findViewById(R.id.first_launch_choose_mik_text_doc);
		brandLeft = (ImageView)view.findViewById(R.id.first_launch_choose_milk_brand_left_icon);
		brandRight = (ImageView)view.findViewById(R.id.first_launch_choose_milk_brand_right_icon);
	}
	@Override
	protected Fragment nextFragment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void inAnimation() {
		// TODO Auto-generated method stub
		TranslateAnimation translateAnimation = new TranslateAnimation(-width/2, 0, 0, 0);
		translateAnimation.setDuration(TIME);
		translateAnimation.setFillAfter(true);
		brandLeft.startAnimation(translateAnimation);
		
		translateAnimation = new TranslateAnimation(width/2, 0, 0, 0);
		translateAnimation.setDuration(TIME);
		translateAnimation.setFillAfter(true);
		brandRight.startAnimation(translateAnimation);
		
		alphAnimation(text, 1f, TIME);
		alphAnimation(doc, 1f, TIME);
	}

	@Override
	protected void outAnimation() {
		// TODO Auto-generated method stub
		TranslateAnimation translateAnimation = new TranslateAnimation(0, -width/2, 0, 0);
		translateAnimation.setDuration(TIME);
		translateAnimation.setFillAfter(true);
		brandLeft.startAnimation(translateAnimation);
		
		translateAnimation = new TranslateAnimation(0,width/2, 0, 0);
		translateAnimation.setDuration(TIME);
		translateAnimation.setFillAfter(true);
		brandRight.startAnimation(translateAnimation);
		
		alphAnimation(text, 0f, TIME);
		alphAnimation(doc, 0f, TIME);
		translateAnimation.setAnimationListener(new AnimationListener() {
			
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
				save();
			}
		});
	}
	
	private void enterNextActivity(){
		Intent intent  = new Intent();
		intent.setClass(getActivity(), MainActivity.class);
		getActivity().startActivity(intent);
        getActivity().finish();
	}

    private void save(){
        baby.save(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e != null) {
                    Toast.makeText(getActivity(), "存入宝贝信息失败", Toast.LENGTH_SHORT)
                            .show();
                }
                else{
                    baby.saveInCache(getActivity(), new Base.CacheSavingCallback() {
                        @Override
                        public void done() {
                            enterNextActivity();
                        }
                    });
                }
            }
        });
    }
	private void setPre(){
		lanunchBottom.setPreListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				lanunchBottom.finishDisapper();
				enterPreFragment();
			}
		});
	}
	private void setNext(){
		lanunchBottom.setFinishListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				outAnimation();
			}
		});
	}
}
