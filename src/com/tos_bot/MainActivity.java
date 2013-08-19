package com.tos_bot;

import touchservice.touchDeviceFactory;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends Activity {
	private Button _startServiceButton;
	private Button _stopServiceButton;
	private Spinner _deviceS;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.initDeviceList(); // create device list
		_startServiceButton = (Button) findViewById(R.id.start_button);
		_stopServiceButton = (Button) findViewById(R.id.stop_button);
		_deviceS = (Spinner) findViewById(R.id.deviceList);

		_startServiceButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				EditText serveret;
				EditText deepet;
				serveret = (EditText) findViewById(R.id.serverUrlText);
				deepet = (EditText) findViewById(R.id.maxMoveText);
				ConfigData.Serverurl = serveret.getText().toString();
				ConfigData.deep = Integer.parseInt(deepet.getText().toString());
				Spinner deviceS = (Spinner) findViewById(R.id.deviceList);
				ConfigData.DeviceName = deviceS.getSelectedItem().toString();
				Intent intent = new Intent(MainActivity.this, botService.class);
				startService(intent);

			}
		});

		_stopServiceButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ConfigData.pasCDid = "";
				Intent intent = new Intent(MainActivity.this, botService.class);
				stopService(intent);
			}
		});

	}

	@Override
	public void onStart() {
		super.onStart();
		ConfigData.pasCDid = "";
		Intent intent = new Intent(MainActivity.this, botService.class);
		stopService(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void initDeviceList() {
		Spinner spinner = (Spinner) findViewById(R.id.deviceList);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item,
				touchDeviceFactory.getDeviceList());
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}

}
