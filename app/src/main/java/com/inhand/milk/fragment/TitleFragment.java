package com.inhand.milk.fragment;

import com.inhand.milk.R;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;

public class TitleFragment extends Fragment{
	protected View mView;
	
	public void refresh(){
		
	}
	protected void  setTitleview(String text , int type){
		
		switch (type) {
		case 0:
			 MainTitle title =  new MainTitle(text);
			 LinearLayout layout = (LinearLayout)mView.findViewById(R.id.title);
			 layout.addView(title.getView(this.getActivity()));
			 break;
		case 1:
			 TemperatureMilkTitle title2 =  new TemperatureMilkTitle(text);
			 LinearLayout layout2 = (LinearLayout)mView.findViewById(R.id.title);
			 layout2.addView(title2.getView(this.getActivity()));
			 break;
		case 2:
			 BackTitle title3 = new BackTitle(text);
			 LinearLayout layout3 = (LinearLayout)mView.findViewById(R.id.title);
			 layout3.addView(title3.getView(this.getActivity()));
			 break;
		default:
			break;
		}
		
		
	}
	protected Fragment  getNextFragment() {
		return null;
	}

	
	protected void gotoNextFragment(){
		FragmentManager manager = TitleFragment.this.getActivity().getFragmentManager();
		FragmentTransaction fragmentTransaction = manager.beginTransaction();
		fragmentTransaction.add(R.id.Activity_fragments_container, getNextFragment());
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}
}
