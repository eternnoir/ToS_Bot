package com.tos_bot;

import java.util.ArrayList;

import puzzleslove.puzzleSolver;
import puzzleslove.solution;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	private Button _startServiceButton;
	private Button _stopServiceButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		_startServiceButton = (Button) findViewById(R.id.start_button);
		_stopServiceButton = (Button) findViewById(R.id.stop_button);
		ConfigData.deep =8;
		ConfigData.Serverurl="http://127.0.0.1:3000";
		ConfigData.eightd = 2;
		touchService.commandDone =true;

		_startServiceButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				EditText serveret;
				EditText deepet;
				serveret = (EditText)findViewById(R.id.serverUrlText);
				deepet = (EditText)findViewById(R.id.maxMoveText);
				ConfigData.Serverurl = serveret.getText().toString();
				ConfigData.deep = Integer.parseInt(deepet.getText().toString());
				Intent intent = new Intent(MainActivity.this, botService.class);
				startService(intent);
				
			}
		});
		
		_stopServiceButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, botService.class);
				stopService(intent);
				
			}
		});

		
	}
	@Override
	public void onStart(){
		super.onStart();
		Intent intent = new Intent(MainActivity.this, botService.class);
		stopService(intent);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
