package com.tos_bot;

import com.tos_bot.touchservice.touchDeviceFactory;
import com.tos_bot.botService;

import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
	private Spinner _serverList;
	private ImageButton _floatStartButtonView = null;
	private ImageButton _floatStopButtonView = null;
	private ImageButton _floatStrategyButtonView = null;
	private ImageButton _floatComboButtonView = null;
	private LinearLayout _floatStrategyLayout = null;
	private LinearLayout _floatComboLayout = null;
	private LinearLayout _floatSaveLayout = null;
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
		this.initServerList(); //create Server List
		//magicat   Environment.getExternalStorageDirectory().getAbsolutePath()
		ConfigData.TempDir = Environment.getExternalStorageDirectory().getAbsolutePath();
		//ConfigData.TempDir = getCacheDir() + "";
		_startServiceButton = (Button) findViewById(R.id.start_button);
		_stopServiceButton = (Button) findViewById(R.id.stop_button);
		_deviceS = (Spinner) findViewById(R.id.deviceList);
		_serverList = (Spinner) findViewById(R.id.serverList);
		
		//按下開始的方框按鈕
		_startServiceButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				EditText deepet;
				//EditText maxcombo;
				//magicat
				EditText sleeptime;			
			
				deepet = (EditText) findViewById(R.id.maxMoveText);
				//CheckBox edcheck = (CheckBox) findViewById(R.id.eightDircheck);
				//maxcombo = (EditText) findViewById(R.id.MaxComboText);
				//magicat
				sleeptime = (EditText) findViewById(R.id.SleepTimeText);

				ConfigData.Serverurl = _serverList.getSelectedItem().toString();
				ConfigData.deep = Integer.parseInt(deepet.getText().toString());
				ConfigData.DeviceName = _deviceS.getSelectedItem().toString();
				//ConfigData.MaxComboName = maxcombo.getText().toString();
				//magicat
				ConfigData.waitForStageChageTimeSec = Integer.parseInt(sleeptime.getText().toString());

//				if (edcheck.isChecked()) {
//					ConfigData.eightd = 1;
//				} else {
//					ConfigData.eightd = 0;
//				}
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

		//按下結束的方框按鈕
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
					if (_floatSaveLayout != null) {
						_wm.removeView(_floatSaveLayout);
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
				_floatSaveLayout = null;
				Intent intent = new Intent(MainActivity.this, botService.class);
				stopService(intent);
				//magicat
				botService.MessageHandler.removeCallbacksAndMessages(botService.MessageHandler);
			}
		});

	}//按鈕設定結束
	


	@Override
	public void onStart() {
		super.onStart();
		Intent intent = new Intent(MainActivity.this, botService.class);
		stopService(intent);
	}

	@Override //MENU 選單
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item){
		super.onOptionsItemSelected(item);
		switch(item.getItemId())
		{
		case R.id.loadfile_auto01:
			loadfileUI(FileLoader.SaveFileNameList[0]);
			break;
		case R.id.loadfile_auto02:
			loadfileUI(FileLoader.SaveFileNameList[1]);
			break;
		case R.id.loadfile_auto03:
			loadfileUI(FileLoader.SaveFileNameList[2]);
			break;
		case R.id.loadfile_start:
			loadfileUI(FileLoader.SaveFileNameList[3]);
			break;				
		case R.id.loadfile_user01:
			loadfileUI(FileLoader.SaveFileNameList[4]);
			break;
		case R.id.loadfile_user02:
			loadfileUI(FileLoader.SaveFileNameList[5]);
			break;
		case R.id.loadfile_user03:
			loadfileUI(FileLoader.SaveFileNameList[6]);
			break;	
		case R.id.loadfile_clear:
			clearfileUI("clear");
			break;				
		}
		return true;
	}
	
	//讀檔UI
	private void loadfileUI(final String filename){
		new AlertDialog.Builder(this)
		.setTitle("確定讀檔? 檔名: "+filename)
		.setPositiveButton
		(
			"確定讀取存檔;   返回鍵跳出",
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					FileLoader.LoadAndSave(filename,false);  //False:load  True:save
				}
			}
		)
		.show();
	}
	
	//清除存檔UI
	private void clearfileUI(final String filename){
		new AlertDialog.Builder(this)
		.setTitle("確定刪除全部存檔??!!")
		.setPositiveButton
		(
			"按下確定清空存檔;   返回鍵跳出",
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					FileLoader.LoadAndSave(filename,false); 
				}
			}
		)
		.show();
	}
	


	private void initDeviceList() {
		Spinner spinner = (Spinner) findViewById(R.id.deviceList);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item,
				touchDeviceFactory.getDeviceList());
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}

	private void initServerList() {
		Spinner spinner = (Spinner) findViewById(R.id.serverList);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item,
				new String[]{"tbserver.ap01.aws.af.cm","tbdserver.ap01.aws.af.cm","tbserver.herokuapp.com"});
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
			@Override
			public void onClick(View view) {//按下浮動開始按鈕的動作
				_floatStartButtonView.setVisibility(View.INVISIBLE);
				//_floatStrategyButtonView.setVisibility(View.INVISIBLE);
				//_floatComboButtonView.setVisibility(View.INVISIBLE);
				if (_floatStopButtonView == null)
					createFStopButton();
				else
					_floatStopButtonView.setVisibility(View.VISIBLE);
				
				Intent intent = new Intent(MainActivity.this, botService.class);
				startService(intent);
				FileLoader.LoadAndSave("start",true);  //False:load  True:save  //開始執行時  存檔先 BJ4
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
			@Override
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
				//magicat
				botService.MessageHandler.removeCallbacksAndMessages(botService.MessageHandler);
				
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
		WindowManager.LayoutParams wmParams = getFloatingLayoutParams(0 + display.getWidth() / 8, display.getHeight() / 4 - 50);

		_floatStrategyButtonView.getBackground().setAlpha(0);
		_floatStrategyButtonView.setImageBitmap(getBitmapByFilename(IdStringMap.get(ConfigData.StyleName) + "_Button"));
		
		_floatStrategyButtonView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (_floatStrategyLayout == null) {
					createFStrategyHorizontalScrollView();
				} else {
					_floatStrategyLayout.setVisibility(View.VISIBLE);
					_floatComboLayout.setVisibility(View.VISIBLE);
					_floatSaveLayout.setVisibility(View.VISIBLE);
				}
				_floatComboButtonView.setVisibility(View.INVISIBLE);
				_floatStartButtonView.setVisibility(View.INVISIBLE);
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
		WindowManager.LayoutParams wmParams = getFloatingLayoutParams(100 + display.getWidth() / 8 , display.getHeight() / 4 - 50);

		_floatComboButtonView.getBackground().setAlpha(0);
		_floatComboButtonView.setImageBitmap(getBitmapByFilename(MaxComboMap.get(ConfigData.MaxComboName) + "_Button"));
		
		_floatComboButtonView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (_floatComboLayout == null) {
					createFStrategyHorizontalScrollView();
				} else {
					_floatStrategyLayout.setVisibility(View.VISIBLE);
					_floatComboLayout.setVisibility(View.VISIBLE);
					_floatSaveLayout.setVisibility(View.VISIBLE);
				}
				_floatStrategyButtonView.setVisibility(View.INVISIBLE);
				_floatStartButtonView.setVisibility(View.INVISIBLE);
				_floatComboButtonView.setVisibility(View.INVISIBLE);
				//_floatStartButtonView.setVisibility(View.INVISIBLE);
			}
		});
		_wm.addView(_floatComboButtonView, wmParams);
	}

	//建立頂部選單
	private void createFStrategyHorizontalScrollView() {
		//建立頂部選單-Strategy		
		_floatStrategyLayout = new LinearLayout(getApplicationContext());
		_wm = (WindowManager) getApplicationContext().getSystemService("window");
		WindowManager.LayoutParams wmParams = getFloatingLayoutParams(0, 0);
		HorizontalScrollView StrategyScrollView = new HorizontalScrollView(this);
		StrategyScrollView.addView(getStrategyLinearLayout());
		_floatStrategyLayout.addView(StrategyScrollView);
		_wm.addView(_floatStrategyLayout, wmParams);
		
		//建立頂部選單-COMBO 
		_floatComboLayout = new LinearLayout(getApplicationContext());
		//_wm = (WindowManager) getApplicationContext().getSystemService("window");
		WindowManager.LayoutParams wmComboParams = getFloatingLayoutParams(0, 70);
		HorizontalScrollView ComboScrollView = new HorizontalScrollView(this);
		ComboScrollView.addView(getComboLinearLayout());		
		_floatComboLayout.addView(ComboScrollView);
		_wm.addView(_floatComboLayout, wmComboParams);
		
		//建立頂部選單-SAVE
		_floatSaveLayout = new LinearLayout(getApplicationContext());
		//_wm = (WindowManager) getApplicationContext().getSystemService("window");
		WindowManager.LayoutParams wmSaveParams = getFloatingLayoutParams(0, 130);
		HorizontalScrollView SaveScrollView = new HorizontalScrollView(this);
		SaveScrollView.addView(getSaveLinearLayout());		
		_floatSaveLayout.addView(SaveScrollView);
		_wm.addView(_floatSaveLayout, wmSaveParams);
	}//建立頂部選單-結束


	private WindowManager.LayoutParams getFloatingLayoutParams(int x, int y) {
		WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
		wmParams.gravity = Gravity.TOP | Gravity.LEFT;
		wmParams.x = x;
		wmParams.y = y;
		wmParams.type = 2002;
		wmParams.format = 1;
		wmParams.flags = 40;
		wmParams.width = LayoutParams.WRAP_CONTENT;
		wmParams.height = LayoutParams.WRAP_CONTENT;
		return wmParams;
	}

	//建立頂部選單圖案-Strategy
	private LinearLayout getStrategyLinearLayout() {
		LinearLayout layout = new LinearLayout(this);
		Integer[] styleList = WeightMapBall.getInstance().getStyleList();
		for (int i = 0; i < styleList.length; i++) {
			layout.addView(getStrategyImageButton(styleList[i]));
		}
		return layout;
	}
	//建立頂部選單圖案-Combo
	private LinearLayout getComboLinearLayout() {
		LinearLayout layout = new LinearLayout(this);
		Integer[] ComboList = WeightMapCombo.getInstance().getStyleList();
		for (int i = 0; i < ComboList.length; i++) {
			layout.addView(getComboImageButton(ComboList[i]));
		}
		return layout;
	}
	//建立頂部選單圖案-Save
	private LinearLayout getSaveLinearLayout() {
		LinearLayout layout = new LinearLayout(this);
		for (int i = 4; i < FileLoader.SaveFileNameList.length; i++) {
			layout.addView(getSaveImageButton(FileLoader.SaveFileNameList[i]));
		}
		return layout;
	}

	//頂部按鈕觸發反應-Strategy
	private ImageButton getStrategyImageButton(Integer styleName) {
		ImageButton button = new ImageButton(this);
		button.getBackground().setAlpha(0);
		button.setId(styleName);
		button.setImageBitmap(getBitmapByFilename(IdStringMap.get(styleName)));
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ConfigData.StyleName = view.getId();	
				_floatStrategyButtonView
				.setImageBitmap(getBitmapByFilename(IdStringMap.get(ConfigData.StyleName) + "_Button"));
				_floatStrategyLayout.setVisibility(View.INVISIBLE);
				_floatComboLayout.setVisibility(View.INVISIBLE);
				_floatSaveLayout.setVisibility(View.INVISIBLE);
				//_floatStartButtonView.setVisibility(View.VISIBLE);
				_floatStrategyButtonView.setVisibility(View.VISIBLE);
				_floatComboButtonView.setVisibility(View.VISIBLE);

					_floatStartButtonView.setVisibility(View.VISIBLE);
				Toast message = Toast.makeText(getApplicationContext(),
						"Weight Strategy : " + IdStringMap.get(ConfigData.StyleName),
						Toast.LENGTH_SHORT); 
				message.setGravity(Gravity.TOP, 0, 50);
				message.show();
			}
		});
		return button;
	}
	
	//頂部按鈕觸發反應-COMBO
	private ImageButton getComboImageButton(Integer ComboName) {
		ImageButton button = new ImageButton(this);
		button.getBackground().setAlpha(0);
		button.setId(ComboName);
		button.setImageBitmap(getBitmapByFilename(MaxComboMap.get(ComboName) + "_Button"));
		
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ConfigData.MaxComboName = view.getId();					
				_floatComboButtonView
				.setImageBitmap(getBitmapByFilename(MaxComboMap.get(ConfigData.MaxComboName) + "_Button"));
				_floatComboLayout.setVisibility(View.INVISIBLE);
				_floatStrategyLayout.setVisibility(View.INVISIBLE);
				_floatSaveLayout.setVisibility(View.INVISIBLE);
				//_floatStartButtonView.setVisibility(View.VISIBLE);
				_floatStrategyButtonView.setVisibility(View.VISIBLE);
				_floatComboButtonView.setVisibility(View.VISIBLE);

					_floatStartButtonView.setVisibility(View.VISIBLE);
				//magicat show Weight Strategy message
				Toast message = Toast.makeText(getApplicationContext(),
						"MaxCombo:" + MaxComboMap.get(ConfigData.MaxComboName)
						,Toast.LENGTH_SHORT);
				message.setGravity(Gravity.TOP, 0, 50);
				message.show();

			}
		});
		return button;
	}
	
	//頂部按鈕觸發反應-Save
	private ImageButton getSaveImageButton(final String SaveFileName) {
		ImageButton button = new ImageButton(this);
		button.getBackground().setAlpha(0);
		button.setImageBitmap(getBitmapByFilename("0_Save_"+SaveFileName));
		
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				FileLoader.LoadAndSave(SaveFileName,true);			
				_floatStrategyLayout.setVisibility(View.INVISIBLE);
				_floatComboLayout.setVisibility(View.INVISIBLE);
				_floatSaveLayout.setVisibility(View.INVISIBLE);
				//_floatStartButtonView.setVisibility(View.VISIBLE);
				_floatStrategyButtonView.setVisibility(View.VISIBLE);
				_floatComboButtonView.setVisibility(View.VISIBLE);

					_floatStartButtonView.setVisibility(View.VISIBLE);
				//magicat show Weight Strategy message
				Toast message = Toast.makeText(getApplicationContext(),
						"Save to :" + SaveFileName
						,Toast.LENGTH_SHORT);
				message.setGravity(Gravity.TOP, 0, 250);
				message.show();

			}
		});
		return button;
	}

	private Bitmap getBitmapByFilename(String filename) {
		//Log.e("getBitmapByFilename(String filename)","image/"+ filename + ".png");
		FileLoader.setContext(this);
		InputStream imageInputStream = FileLoader.getFileStreamByAsset("image/" + filename + ".png");
		return BitmapFactory.decodeStream(imageInputStream);
	}



}
