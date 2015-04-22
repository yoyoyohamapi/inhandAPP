package com.inhand.milk.activity;

import com.inhand.milk.R;
import com.inhand.milk.fragment.health.nutrition.Nutrition;
import com.inhand.milk.utils.HealthNutritionDisk;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class HealthNutritionActivity extends SubActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	protected Fragment initFragment() {
		// TODO Auto-generated method stub
		Fragment mFragment = new Nutrition();
		return mFragment;
	}
		

}
