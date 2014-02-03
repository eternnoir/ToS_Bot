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
				setConfig();
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
	
	/**
	 * get config data from SharedPreferences
	 */
	private void setConfig(){
		SharedPreferences settings = getSharedPreferences("Config", 0);
		ConfigData.Serverurl = settings.getString("Serverurl", "http://tbserver.ap01.aws.af.cm/");
		ConfigData.deep = settings.getInt("deep", 30);
		ConfigData.maxCombo = settings.getString("maxCombo", "0");
		ConfigData.gap = settings.getString("gap","");
		ConfigData.DeviceName =  settings.getString("DeviceName","Auto");
		ConfigData.touchEventNum = settings.getString("touchEventNum","");
		ConfigData.posXId = settings.getString("posXId","");
		ConfigData.posYId = settings.getString("posYId","");
		ConfigData.posXMax = settings.getString("posXMax","");
		ConfigData.posYMax = settings.getString("posYMax","");
		ConfigData.trackingId = settings.getString("trackingId","");
		ConfigData.pressureId = settings.getString("pressureId","");
		ConfigData.trackingMax = settings.getString("trackingMax","");
		ConfigData.pressureMax = settings.getString("pressureMax","");
		ConfigData.oneBallMove = settings.getString("oneBallMove","");
		ConfigData.startPosX = settings.getString("startPosX","");
		ConfigData.startPosY = settings.getString("startPosY","");
	}
}
