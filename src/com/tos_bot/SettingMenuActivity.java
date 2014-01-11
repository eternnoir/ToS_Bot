package com.tos_bot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SettingMenuActivity extends Activity {
	
	private ListView _settingList;
	private final String[] _settingStrings = new String[] {
			"General","Image","TouchEvent"
		};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settingmenu);
		_settingList = (ListView) findViewById(R.id.setListView);
		_settingList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,_settingStrings));
		_settingList.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				switch(pos){
					case 0:
						break;
					case 1:
						break;
					case 2:
						Intent setMenu = new Intent();
						setMenu.setClass(SettingMenuActivity.this, EventSettingActivity.class);
						startActivity(setMenu);
						break;
				}
			}
		   });
	}
	
	
	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
