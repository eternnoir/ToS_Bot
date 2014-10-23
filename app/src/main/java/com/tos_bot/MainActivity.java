package com.tos_bot;

import com.tos_bot.ui.FloatingUIManager;
import com.tos_bot.ui.Observer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

/**
 * @author frankwang
 *
 */
public class MainActivity extends Activity {
	private Button _startServiceButton;
	private Button _stopServiceButton;
	private Button _settingMenuButton;
    public Observer _observer = new Observer(this);
    private FloatingUIManager floatingUI;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.initDeviceList(); // create device list

		ConfigData.TempDir = getCacheDir() + "";
        floatingUI = new FloatingUIManager(
                this.getApplicationContext(),
                this.getWindowManager().getDefaultDisplay(),
                _observer);
		_startServiceButton = (Button) findViewById(R.id.start_button);
		_startServiceButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				ConfigData.setConfig(getSharedPreferences("Config", 0));
                floatingUI.StartFloatingUI();
			}
		});

		_settingMenuButton = (Button) findViewById(R.id.setting_button);
		_settingMenuButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent setMenu = new Intent(MainActivity.this, SettingMenuActivity.class);
				startActivity(setMenu);
			}
		});
		_stopServiceButton=(Button) findViewById(R.id.stopButton);
		_stopServiceButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
                floatingUI.StopFloatingUI();
                StopService();
			}
		});

	}

	@Override
	public void onStart() {
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

	private void initDeviceList() {

	}

	/**
	 *
	 */

    public void StartService(){
        Intent intent = new Intent(MainActivity.this, botService.class);
        startService(intent);
    }

    public void StopService(){
        Intent intent = new Intent(MainActivity.this, botService.class);
        stopService(intent);
    }
	
}
