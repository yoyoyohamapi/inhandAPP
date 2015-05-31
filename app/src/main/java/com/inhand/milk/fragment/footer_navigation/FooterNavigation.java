package com.inhand.milk.fragment.footer_navigation;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.inhand.milk.R;
import com.inhand.milk.fragment.health.HealthFragment;
import com.inhand.milk.fragment.home.HomeFragment;
import com.inhand.milk.fragment.temperature_amount.AmountStatistics;
import com.inhand.milk.fragment.temperature_amount.TemperatureStatistics;

public class FooterNavigation extends Fragment {

	private View view;
	private TemperatureStatistics tempreture;
	private AmountStatistics amount;
	private HomeFragment home;
	private HealthFragment health;
	private FooterButtonsManager buttonsManager ;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view  = inflater.inflate(R.layout.buttons, null);
		Log.i("buttons", "oncreateview");
		initButtons();
		return view;
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.i("buttons", "onStart");
		//attachHome();
		
	}
	/*
	private void attachHome(){
		FragmentManager  fragmentManager = getFragmentManager();  
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		if (home ==null)
			home = new HomeFragment();
		fragmentTransaction.add(R.id.Activity_fragments_container, home,"HOME"); 
        fragmentTransaction.commit();  
	}
	
	private void initButtons(){
		 ImageButton button1 = (ImageButton)view.findViewById(R.id.buttons_milk_icon);
		 button1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					FragmentManager fragmentManager = getFragmentManager();  
					FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction(); 
					
					switch (currentFragment) {
					case  HOME:
						fragmentTransaction.hide(home);
						break;
					case AMOUNT:
						return;
					case TEMPERATURE:
						fragmentTransaction.hide(tempreture);
						break;
					case PERSONCENTER:
						fragmentTransaction.hide(bluetooth);
						break;
					case HEALTH:
						fragmentTransaction.hide(health);
						break;
					}
					currentFragment = CurrentFragment.AMOUNT;
					
					if(amount == null){
						amount =  new TempretureMilkFragment();
						amount.isTemperature(false);
						fragmentTransaction.add(R.id.Activity_fragments_container, amount, "AMOUNT");
					}
					else{
						fragmentTransaction.show(amount);
						amount.refresh();
					}
					fragmentTransaction.commit();
					
				}    
			});
		 
		 ImageButton button2 = (ImageButton)view.findViewById(R.id.buttons_temperature_icon);
		 button2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					FragmentManager fragmentManager = getFragmentManager();  
					FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction(); 
					
					switch (currentFragment) {
					case  HOME:
						fragmentTransaction.hide(home);
						break;
					case AMOUNT:
						fragmentTransaction.hide(amount);
						break;
					case TEMPERATURE:
						return;
					case PERSONCENTER:
						fragmentTransaction.hide(bluetooth);
						break;
					case HEALTH:
						fragmentTransaction.hide(health);
						break;
					}
					currentFragment = CurrentFragment.TEMPERATURE;
					
					if(tempreture == null){
						tempreture =  new TempretureMilkFragment();
						tempreture.isTemperature(true);
						fragmentTransaction.add(R.id.Activity_fragments_container, tempreture, "TENPRETURE");
					}
					else{
						fragmentTransaction.show(tempreture);
						tempreture.refresh();
					}	
					fragmentTransaction.commit();	
				}    
			});
		 
		 ImageButton button3 = (ImageButton)view.findViewById(R.id.buttons_home);
		 button3.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					FragmentManager fragmentManager = getFragmentManager();  
					FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction(); 
		 
					switch (currentFragment) {
					case  HOME:
						return;
					case AMOUNT:
						fragmentTransaction.hide(amount);
						break;
					case TEMPERATURE:
						fragmentTransaction.hide(tempreture);
						break;
					case PERSONCENTER:
						fragmentTransaction.hide(bluetooth);
						break;
					case HEALTH:
						fragmentTransaction.hide(health);
						break;
					}
					currentFragment  = CurrentFragment.HOME;	
					fragmentTransaction.show(home);
					fragmentTransaction.commit();
				}    
			});
		 
		 ImageButton button4 = (ImageButton)view.findViewById(R.id.buttons_health);
		 button4.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					FragmentManager fragmentManager = getFragmentManager();  
					FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction(); 
		 
					switch (currentFragment) {
					case  HOME:
						fragmentTransaction.hide(home);
						break;
					case AMOUNT:
						fragmentTransaction.hide(amount);
						break;
					case TEMPERATURE:
						fragmentTransaction.hide(tempreture);
						break;
					case PERSONCENTER:
						fragmentTransaction.hide(bluetooth);
						break;
					case HEALTH:
						return;
					}
					currentFragment  = CurrentFragment.HEALTH;
					
					if(health == null){
						health = new HealthFragment();
						fragmentTransaction.add(R.id.Activity_fragments_container, health, "HEALTH");
					}
					else 
						fragmentTransaction.show(health);
					
					fragmentTransaction.commit();
				}    
			});
		
		 ImageButton button5 = (ImageButton)view.findViewById(R.id.buttons_person_center);
		 button5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentManager fragmentManager = getFragmentManager();  
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction(); 
				
				switch (currentFragment) {
					case  HOME:
						fragmentTransaction.hide(home);
						break;
					case AMOUNT:
						fragmentTransaction.hide(amount);
						break;
					case TEMPERATURE:
						fragmentTransaction.hide(tempreture);
						break;
					case PERSONCENTER:
						return;
					case HEALTH:
						fragmentTransaction.hide(health);
						break;
				}
				currentFragment = CurrentFragment.PERSONCENTER;
				
				if(bluetooth == null){
					bluetooth = new bluetooth_fragment(  ((MainActivity)FooterNavigation.this.getActivity() ).getBluetooth());
					fragmentTransaction.add(R.id.Activity_fragments_container, bluetooth,"BLUETOOTH");
				}
				else {
					fragmentTransaction.show(bluetooth);
				}
				fragmentTransaction.commit();
			}		
		});
		 
	}
	*/
	private void initButtons(){
		FragmentManager fragmentManager = getFragmentManager();
		buttonsManager = new FooterButtonsManager( fragmentManager);
		tempreture = new TemperatureStatistics();
		amount = new AmountStatistics();
		health = new HealthFragment();
		home = new HomeFragment();
		//bluetooth = new bluetooth_fragment(((MainActivity)FooterNavigation.this.getActivity() ).getBluetooth() );
		ImageButton button ;
		button = (ImageButton)view.findViewById(R.id.buttons_home);
		buttonsManager.addButtons(button,home);
		buttonsManager.setStartFragment(button);
		
		button = (ImageButton)view.findViewById(R.id.buttons_temperature_icon);
		buttonsManager.addButtons(button,tempreture);
		
		button = (ImageButton)view.findViewById(R.id.buttons_milk_icon);
		buttonsManager.addButtons(button,amount);
		
		button = (ImageButton)view.findViewById(R.id.buttons_health);
		buttonsManager.addButtons(button,health);	
		
		//button = (ImageButton)view.findViewById(R.id.buttons_person_center);
		//buttonsManager.addButtons(button,bluetooth);		
	}

}
	
	

