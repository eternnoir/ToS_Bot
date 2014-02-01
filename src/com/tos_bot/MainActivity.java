package com.tos_bot;

import com.tos_bot.ui.FloatingUIManager;
import com.tos_bot.ui.Observer;
import com.tos_bot.utility.FileLoader;

import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ImageButton;

import java.io.InputStream;
import java.util.LinkedHashMap;

/**
 * @author frankwang
 *
 */
public class MainActivity extends Activity {
	private Button _startServiceButton;
	private Button _stopServiceButton;
	private Button _settingMenuButton;
	private ImageButton _floatStartButtonView = null;
	private ImageButton _floatStopButtonView = null;
	private ImageButton _floatStrategyButtonView = null;
	private LinearLayout _floatStrategyLayout = null;
	private WindowManager _wm = null;
    public Observer _observer = new Observer(this);
    private FloatingUIManager floatingUI;
	private final LinkedHashMap<Integer, String> IdStringMap = new LinkedHashMap<Integer, String>() {
		{
			put(R.id.Vary_color_Single, "Vary_color_Single");
			put(R.id.Vary_color_Multi, "Vary_color_Multi");
			put(R.id.Water_Single, "Water_Single");
			put(R.id.Water_Multi, "Water_Multi");
			put(R.id.Fire_Single, "Fire_Single");
			put(R.id.Fire_Multi, "Fire_Multi");
			put(R.id.Wood_Single, "Wood_Single");
			put(R.id.Wood_Multi, "Wood_Multi");
			put(R.id.Light_Single, "Light_Single");
			put(R.id.Light_Multi, "Light_Multi");
			put(R.id.Dark_Single, "Dark_Single");
			put(R.id.Dark_Multi, "Dark_Multi");
			put(R.id.Recover_Single, "Recover_Single");
			put(R.id.Recover_Multi, "Recover_Multi");
			put(R.id.Water_Except, "Water_Except");
			put(R.id.Fire_Except, "Fire_Except");
			put(R.id.Wood_Except, "Wood_Except");
			put(R.id.Light_Except, "Light_Except");
			put(R.id.Dark_Except, "Dark_Except");
			put(R.id.Recover_Except, "Recover_Except");
			/*put(R.id.Low_HP_Single, "Low_HP_Single");
			put(R.id.Low_HP_Multi, "Low_HP_Multi");*/
		}
	};

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
		ConfigData.maxBombo = settings.getString("maxBombo", "0");
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
