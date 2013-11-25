package com.tos_bot;

import com.tos_bot.touchservice.touchDeviceFactory;
import com.tos_bot.botService;

import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
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
import android.widget.Toast;

import java.io.InputStream;
import java.util.LinkedHashMap;

public class MainActivity extends Activity {
	private Button _startServiceButton;
	private Button _stopServiceButton;
	private Spinner _deviceS;
	private ImageButton _floatStartButtonView = null;
	private ImageButton _floatStopButtonView = null;
	private ImageButton _floatStrategyButtonView = null;
	private ImageButton _floatComboButtonView = null;
	private LinearLayout _floatStrategyLayout = null;
	private LinearLayout _floatComboLayout = null;
	private WindowManager _wm = null;
	public LinkedHashMap<Integer, String> MaxComboMap = new LinkedHashMap<Integer, String>() {
		{
			put(R.MaxCombo.Combo_0, "Combo_0");
			put(R.MaxCombo.Combo_1, "Combo_1");
			put(R.MaxCombo.Combo_2, "Combo_2");
			put(R.MaxCombo.Combo_3, "Combo_3");
			put(R.MaxCombo.Combo_4, "Combo_4");
			put(R.MaxCombo.Combo_5, "Combo_5");
			put(R.MaxCombo.Combo_6, "Combo_6");
		}
	};
	public LinkedHashMap<Integer, String> IdStringMap = new LinkedHashMap<Integer, String>() {
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
			//magicat
//			put(R.id.Low_HP_Single, "Low_HP_Single");
//			put(R.id.Low_HP_Multi, "Low_HP_Multi");
		}
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.initDeviceList(); // create device list

		//magicat   Environment.getExternalStorageDirectory().getAbsolutePath()
		ConfigData.TempDir = Environment.getExternalStorageDirectory().getAbsolutePath();
		//ConfigData.TempDir = getCacheDir() + "";
		_startServiceButton = (Button) findViewById(R.id.start_button);
		_stopServiceButton = (Button) findViewById(R.id.stop_button);
		_deviceS = (Spinner) findViewById(R.id.deviceList);
		_startServiceButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				EditText serveret;
				EditText deepet;
				//EditText maxcombo;
				//magicat
				EditText sleeptime;
				serveret = (EditText) findViewById(R.id.serverUrlText);
				deepet = (EditText) findViewById(R.id.maxMoveText);
				CheckBox edcheck = (CheckBox) findViewById(R.id.eightDircheck);
				//maxcombo = (EditText) findViewById(R.id.MaxComboText);
				//magicat
				sleeptime = (EditText) findViewById(R.id.SleepTimeText);

				ConfigData.Serverurl = serveret.getText().toString();
				ConfigData.deep = Integer.parseInt(deepet.getText().toString());
				ConfigData.DeviceName = _deviceS.getSelectedItem().toString();
				//ConfigData.MaxComboName = maxcombo.getText().toString();
				//magicat
				ConfigData.waitForStageChageTimeSec = Integer.parseInt(sleeptime.getText().toString());

				if (edcheck.isChecked()) {
					ConfigData.eightd = 1;
				} else {
					ConfigData.eightd = 0;
				}
				if (_floatStartButtonView == null) {
					createFStartButton();
				}
				if (_floatStrategyButtonView == null) {
					createFStrategyButton();
				}
				if (_floatComboButtonView == null) {
					createComboButton();
				}
			}
		});

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
					if (_floatComboButtonView != null) {
						_wm.removeView(_floatComboButtonView);
					}
					if (_floatComboLayout != null) {
						_wm.removeView(_floatComboLayout);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				_floatStartButtonView = null;
				_floatStopButtonView = null;
				_floatStrategyButtonView = null;
				_floatStrategyLayout = null;
				_floatComboButtonView = null;
				_floatComboLayout = null;
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
		Spinner spinner = (Spinner) findViewById(R.id.deviceList);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item,
				touchDeviceFactory.getDeviceList());
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
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
				//_floatStrategyButtonView.setVisibility(View.INVISIBLE);
				//_floatComboButtonView.setVisibility(View.INVISIBLE);
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
				if (_floatComboButtonView == null) {
					createFStrategyButton();
				} else {
					_floatComboButtonView.setVisibility(View.VISIBLE);
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
	
	//建立策略按鈕
	private void createFStrategyButton() {
		Display display = getWindowManager().getDefaultDisplay();
		_floatStrategyButtonView = new ImageButton(getApplicationContext());
		_wm = (WindowManager) getApplicationContext().getSystemService("window");
		WindowManager.LayoutParams wmParams = getFloatingLayoutParams(0 + display.getWidth() * 1 / 8, display.getHeight() * 2 / 8);

		_floatStrategyButtonView.getBackground().setAlpha(0);
		_floatStrategyButtonView.setImageBitmap(getBitmapByFilename(IdStringMap.get(ConfigData.StyleName) + "_Button"));
		
		_floatStrategyButtonView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (_floatStrategyLayout == null) {
					createFStrategyHorizontalScrollView();
				} else {
					_floatStrategyLayout.setVisibility(View.VISIBLE);
				}
				_floatComboButtonView.setVisibility(View.INVISIBLE);
				_floatStrategyButtonView.setVisibility(View.INVISIBLE);
				//_floatStartButtonView.setVisibility(View.INVISIBLE);
			}
		});
		_wm.addView(_floatStrategyButtonView, wmParams);
	}
	
	//建立COMBO按鈕
	private void createComboButton() {
		Display display = getWindowManager().getDefaultDisplay();
		_floatComboButtonView = new ImageButton(getApplicationContext());
		_wm = (WindowManager) getApplicationContext().getSystemService("window");
		//放在策略按鈕右邊100 pixel
		WindowManager.LayoutParams wmParams = getFloatingLayoutParams(100 + display.getWidth() * 1 / 8 , display.getHeight() * 2 / 8);

		_floatComboButtonView.getBackground().setAlpha(0);
		_floatComboButtonView.setImageBitmap(getBitmapByFilename(MaxComboMap.get(ConfigData.MaxComboName) + "_Button"));
		
		_floatComboButtonView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (_floatComboLayout == null) {
					createComboHorizontalScrollView();
				} else {
					_floatComboLayout.setVisibility(View.VISIBLE);
				}
				_floatStrategyButtonView.setVisibility(View.INVISIBLE);
				_floatComboButtonView.setVisibility(View.INVISIBLE);
				//_floatStartButtonView.setVisibility(View.INVISIBLE);
			}
		});
		_wm.addView(_floatComboButtonView, wmParams);
	}

	//建立策略 頂部選單
	private void createFStrategyHorizontalScrollView() {
		_floatStrategyLayout = new LinearLayout(getApplicationContext());
		_wm = (WindowManager) getApplicationContext().getSystemService("window");
		WindowManager.LayoutParams wmParams = getFloatingLayoutParams(0, 0);
		HorizontalScrollView StrategyScrollView = new HorizontalScrollView(this);
		StrategyScrollView.addView(getStrategyLinearLayout());
		_floatStrategyLayout.addView(StrategyScrollView);
		_wm.addView(_floatStrategyLayout, wmParams);
	}
	//建立COMBO 頂部選單
	private void createComboHorizontalScrollView() {
		_floatComboLayout = new LinearLayout(getApplicationContext());
		_wm = (WindowManager) getApplicationContext().getSystemService("window");
		WindowManager.LayoutParams wmParams = getFloatingLayoutParams(0, 0);
		HorizontalScrollView ComboScrollView = new HorizontalScrollView(this);
		ComboScrollView.addView(getComboLinearLayout());
		
		_floatComboLayout.addView(ComboScrollView);
		_wm.addView(_floatComboLayout, wmParams);
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
			layout.addView(getStrategyImageButton(styleList[i]));
		}
		return layout;
	}
	
	private LinearLayout getComboLinearLayout() {
		LinearLayout layout = new LinearLayout(this);
		Integer[] ComboList = ComboWeightMap.getInstance().getStyleList();

		for (int i = 0; i < ComboList.length; i++) {
			layout.addView(getComboImageButton(ComboList[i]));
		}
		
		return layout;
	}

	//頂部策略按鈕觸發反應
	private ImageButton getStrategyImageButton(Integer styleName) {
		ImageButton button = new ImageButton(this);
		button.getBackground().setAlpha(0);
		button.setId(styleName);
		button.setImageBitmap(getBitmapByFilename(IdStringMap.get(styleName)));
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				ConfigData.StyleName = view.getId();	
				_floatStrategyButtonView
				.setImageBitmap(getBitmapByFilename(IdStringMap.get(ConfigData.StyleName) + "_Button"));
				_floatStrategyLayout.setVisibility(View.INVISIBLE);
				//_floatStartButtonView.setVisibility(View.VISIBLE);
				_floatStrategyButtonView.setVisibility(View.VISIBLE);
				_floatComboButtonView.setVisibility(View.VISIBLE);
				Toast.makeText(getApplicationContext(),
						"Weight Strategy : " + IdStringMap.get(ConfigData.StyleName),
						Toast.LENGTH_SHORT).show(); 
			}
		});
		return button;
	}
	
	//頂部COMBO按鈕觸發反應
	private ImageButton getComboImageButton(Integer ComboName) {
		ImageButton button = new ImageButton(this);
		button.getBackground().setAlpha(0);
		button.setId(ComboName);
		button.setImageBitmap(getBitmapByFilename(MaxComboMap.get(ComboName) + "_Button"));
		
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				ConfigData.MaxComboName = view.getId();					
				_floatComboButtonView
				.setImageBitmap(getBitmapByFilename(MaxComboMap.get(ConfigData.MaxComboName) + "_Button"));
				_floatComboLayout.setVisibility(View.INVISIBLE);
				//_floatStartButtonView.setVisibility(View.VISIBLE);
				_floatStrategyButtonView.setVisibility(View.VISIBLE);
				_floatComboButtonView.setVisibility(View.VISIBLE);
				//magicat show Weight Strategy message
				Toast.makeText(getApplicationContext(),
						"MaxComboName Strategy : " + MaxComboMap.get(ConfigData.MaxComboName)
						//"\n getAbsolutePath : " + Environment.getExternalStorageDirectory().getAbsolutePath() +
						//"\n Build.MODEL : " + Build.MODEL +
						//"\n ConfigData.TempDir  : " + ConfigData.TempDir + "/img.png\n" +
						//"\n getCacheDir() : " + getCacheDir(),
						
						//"\n getCacheDir() : " + getCacheDir(),
						//"\n getCacheDir() : " + getCacheDir(),
						//"\n view.getId() : " + view.getId(),					
						,Toast.LENGTH_SHORT).show(); 
			}
		});
		return button;
	}

	private Bitmap getBitmapByFilename(String filename) {
		Log.e("getBitmapByFilename(String filename)",filename);
		
		FileLoader.setContext(this);
		InputStream imageInputStream = FileLoader.getFileStreamByAsset("image/"
				+ filename + ".png");
		return BitmapFactory.decodeStream(imageInputStream);
	}



}
