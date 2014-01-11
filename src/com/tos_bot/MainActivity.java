package com.tos_bot;

import com.tos_bot.touchservice.touchDeviceFactory;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ImageButton;

import java.io.InputStream;
import java.util.LinkedHashMap;

public class MainActivity extends Activity {
	private Button _startServiceButton;
	private Button _stopServiceButton;
	private Button _settingMenuButton;
	private ImageButton _floatStartButtonView = null;
	private ImageButton _floatStopButtonView = null;
	private ImageButton _floatStrategyButtonView = null;
	private LinearLayout _floatStrategyLayout = null;
	private WindowManager _wm = null;
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
			put(R.id.Low_HP_Single, "Low_HP_Single");
			put(R.id.Low_HP_Multi, "Low_HP_Multi");
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.initDeviceList(); // create device list

		ConfigData.TempDir = getCacheDir() + "";
		_startServiceButton = (Button) findViewById(R.id.start_button);
		_startServiceButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				setConfig();
				if (_floatStartButtonView == null) {
					createFStartButton();
				}

				if (_floatStrategyButtonView == null) {
					createFStrategyButton();
				}
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
				try {
					if (_floatStartButtonView != null) {
						_wm.removeView(_floatStartButtonView);
					}
					if (_floatStopButtonView != null) {
						_wm.removeView(_floatStopButtonView);
					}
					if (_floatStrategyButtonView != null) {
						_wm.removeView(_floatStrategyButtonView);
					}
					if (_floatStrategyLayout != null) {
						_wm.removeView(_floatStrategyLayout);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				_floatStartButtonView = null;
				_floatStopButtonView = null;
				_floatStrategyButtonView = null;
				_floatStrategyLayout = null;
				Intent intent = new Intent(MainActivity.this, botService.class);
				stopService(intent);
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

	private void createFStartButton() {
		Display display = getWindowManager().getDefaultDisplay();
		_floatStartButtonView = new ImageButton(getApplicationContext());
		_wm = (WindowManager) getApplicationContext()
				.getSystemService("window");
		WindowManager.LayoutParams wmParams = getFloatingLayoutParams(
				0 + display.getWidth() / 8, display.getHeight() / 8);
		_floatStartButtonView.getBackground().setAlpha(0);
		_floatStartButtonView.setImageBitmap(getBitmapByFilename("start"));
		_floatStartButtonView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				_floatStartButtonView.setVisibility(View.INVISIBLE);
				_floatStrategyButtonView.setVisibility(View.INVISIBLE);
				if (_floatStopButtonView == null)
					createFStopButton();
				else
					_floatStopButtonView.setVisibility(View.VISIBLE);
				Intent intent = new Intent(MainActivity.this, botService.class);
				startService(intent);
			}
		});
		_wm.addView(_floatStartButtonView, wmParams); // ?遣View
	}

	private void createFStopButton() {
		Display display = getWindowManager().getDefaultDisplay();
		_floatStopButtonView = new ImageButton(getApplicationContext());
		_wm = (WindowManager) getApplicationContext()
				.getSystemService("window");
		WindowManager.LayoutParams wmParams = getFloatingLayoutParams(
				display.getWidth() / 2 + display.getWidth() / 8,
				display.getHeight() / 8);
		_floatStopButtonView.getBackground().setAlpha(0);
		_floatStopButtonView.setImageBitmap(getBitmapByFilename("stop"));
		_floatStopButtonView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				_floatStopButtonView.setVisibility(View.INVISIBLE);
				if (_floatStartButtonView == null) {
					createFStartButton();
				} else {
					_floatStartButtonView.setVisibility(View.VISIBLE);
				}
				if (_floatStrategyButtonView == null) {
					createFStrategyButton();
				} else {
					_floatStrategyButtonView.setVisibility(View.VISIBLE);
				}

				if (ConfigData.solverThread != null) {
					Thread moribund = ConfigData.solverThread;
					ConfigData.solverThread = null;
					moribund.interrupt();
				}
				Intent intent = new Intent(MainActivity.this, botService.class);
				stopService(intent);
			}
		});
		_wm.addView(_floatStopButtonView, wmParams);
	}

	private void createFStrategyButton() {
		Display display = getWindowManager().getDefaultDisplay();
		_floatStrategyButtonView = new ImageButton(getApplicationContext());
		_wm = (WindowManager) getApplicationContext()
				.getSystemService("window");
		WindowManager.LayoutParams wmParams = getFloatingLayoutParams(
				0 + display.getWidth() / 8, display.getHeight() * 2 / 8);

		_floatStrategyButtonView.getBackground().setAlpha(0);
		_floatStrategyButtonView.setImageBitmap(getBitmapByFilename(IdStringMap
				.get(ConfigData.StyleName) + "_Button"));
		_floatStrategyButtonView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (_floatStrategyLayout == null) {
					createFStrategyHorizontalScrollView();
				} else {
					_floatStrategyLayout.setVisibility(View.VISIBLE);
				}
				_floatStrategyButtonView.setVisibility(View.INVISIBLE);
				_floatStartButtonView.setVisibility(View.INVISIBLE);
			}
		});
		_wm.addView(_floatStrategyButtonView, wmParams);
	}

	private void createFStrategyHorizontalScrollView() {
		_floatStrategyLayout = new LinearLayout(getApplicationContext());
		_wm = (WindowManager) getApplicationContext()
				.getSystemService("window");
		WindowManager.LayoutParams wmParams = getFloatingLayoutParams(0, 0);

		HorizontalScrollView scrollView = new HorizontalScrollView(this);
		scrollView.addView(getStrategyLinearLayout());
		_floatStrategyLayout.addView(scrollView);
		_wm.addView(_floatStrategyLayout, wmParams);
	}

	private WindowManager.LayoutParams getFloatingLayoutParams(int x, int y) {
		WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
		wmParams.gravity = Gravity.TOP | Gravity.LEFT;
		wmParams.x = x;
		wmParams.y = y;
		wmParams.type = 2002;
		wmParams.format = 1;
		wmParams.flags = 40;
		wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		return wmParams;
	}

	private LinearLayout getStrategyLinearLayout() {
		LinearLayout layout = new LinearLayout(this);
		Integer[] styleList = weightMap.getInstance().getStyleList();

		for (int i = 0; i < styleList.length; i++) {
			layout.addView(getImageButton(styleList[i]));
		}

		return layout;
	}

	private ImageButton getImageButton(Integer styleName) {
		ImageButton button = new ImageButton(this);
		button.getBackground().setAlpha(0);
		button.setId(styleName);
		button.setImageBitmap(getBitmapByFilename(IdStringMap.get(styleName)));
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				ConfigData.StyleName = view.getId();
				_floatStrategyButtonView
						.setImageBitmap(getBitmapByFilename(IdStringMap
								.get(ConfigData.StyleName) + "_Button"));
				_floatStrategyLayout.setVisibility(View.INVISIBLE);
				_floatStartButtonView.setVisibility(View.VISIBLE);
				_floatStrategyButtonView.setVisibility(View.VISIBLE);
			}
		});
		return button;
	}

	private Bitmap getBitmapByFilename(String filename) {
		FileLoader.setContext(this);
		InputStream imageInputStream = FileLoader.getFileStreamByAsset("image/"
				+ filename + ".png");
		return BitmapFactory.decodeStream(imageInputStream);
	}
	
	private void setConfig(){
		SharedPreferences settings = getSharedPreferences("Config", 0);
		ConfigData.Serverurl = settings.getString("Serverurl", "http://tbserver.ap01.aws.af.cm/");
		ConfigData.deep = settings.getInt("deep", 30);
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
