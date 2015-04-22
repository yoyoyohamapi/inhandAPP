
package com.inhand.milk.fragment.bluetooth;

import com.inhand.milk.R;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class bluetooth_fragment extends Fragment{

	View view;
	ListView listView;
	Bluetooth bluetooth;
	ArrayAdapter<String> adapter;
	boolean isServer = false;
	public bluetooth_fragment(Bluetooth btooth) {
		// TODO Auto-generated constructor stub
		bluetooth = btooth;	
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.bluetooth,null);
		listView = (ListView)view.findViewById(R.id.detals_listView);
		//bluetooth.openBlue();
		//bluetooth.startSearch();
		setButton();
		return view;
	}

	private void setButton(){
		Button button = (Button)view.findViewById(R.id.kejian);
		
		  button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bluetooth.discoverable();
			}
		});
		
		  
		  button = (Button)view.findViewById(R.id.sousuo);
		
		  button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bluetooth.startSearch();
			}
		});
		  
		  button = (Button)view.findViewById(R.id.server);
			
		  button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bluetooth.asServer();
				isServer = true;
			}
		}); 
		  
		  
		  
		  button = (Button)view.findViewById(R.id.cilent);
			
		  button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bluetooth.asClient();
				isServer = false;
			}
		}); 
		  
		  
		  button = (Button)view.findViewById(R.id.tijiao);
		  EditText editText = (EditText)view.findViewById(R.id.editText);
		  editText.setTextColor(Color.WHITE);
		  button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isServer){
					EditText editText = (EditText)view.findViewById(R.id.editText);
					String string = editText.getText().toString();
					editText.setText("");
					Toast.makeText(v.getContext(),string, Toast.LENGTH_LONG).show();
					bluetooth.sendStream( string.getBytes());
				}
			}
		}); 
		  
		  
		  
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		adapter = bluetooth.getListViewAdapter();
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.i("position", String.valueOf(position));
				bluetooth.setPaired(position);
				//bluetooth.connect();
				
			}
			
		});
	}

	
	
}
