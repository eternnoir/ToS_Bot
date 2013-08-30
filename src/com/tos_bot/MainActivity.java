package com.tos_bot;

import com.tos_bot.touchservice.touchDeviceFactory;

import android.os.Bundle;
import android.os.StrictMode.VmPolicy;
import android.app.Activity;
import android.content.Intent;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Button _startServiceButton;
	private Button _stopServiceButton;
	private Spinner _deviceS;
    private Spinner _styleList;
	private Button _floatStartButtonView = null;
	private Button _floatStopButtonView = null;
	private WindowManager _wm = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.initDeviceList(); // create device list
        this.initStyleList();
        ConfigData.TempDir = getCacheDir()+"";
		_startServiceButton = (Button) findViewById(R.id.start_button);
		_stopServiceButton = (Button) findViewById(R.id.stop_button);
		_deviceS = (Spinner) findViewById(R.id.deviceList);
        _styleList = (Spinner) findViewById(R.id.styleList);
		_startServiceButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				EditText serveret;
				EditText deepet;
				serveret = (EditText) findViewById(R.id.serverUrlText);
				deepet = (EditText) findViewById(R.id.maxMoveText);
				ConfigData.Serverurl = serveret.getText().toString();
				ConfigData.deep = Integer.parseInt(deepet.getText().toString());
				ConfigData.DeviceName = _deviceS.getSelectedItem().toString();
                ConfigData.StyleName = _styleList.getSelectedItem().toString();
				if(_floatStartButtonView == null){
					createFStartButton();
				}
			}
		});

		_stopServiceButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ConfigData.pasCDid = "";
				try {
					_wm.removeView(_floatStartButtonView);
					_wm.removeView(_floatStopButtonView);
				} catch (Exception e) {
					// TODO: handle exception
				}
				_floatStartButtonView = null;
				_floatStopButtonView = null;
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

    private void initStyleList() {
        Spinner spinner = (Spinner) findViewById(R.id.styleList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                weightMap.getInstance().getStyleList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

	private void createFStartButton() {
		Display display = getWindowManager().getDefaultDisplay();
		_floatStartButtonView = new Button(getApplicationContext());
		_wm = (WindowManager) getApplicationContext()
				.getSystemService("window");
		WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
		wmParams.gravity = Gravity.TOP | Gravity.LEFT ;
		wmParams.x = 0+display.getWidth()/8;
		wmParams.y = display.getHeight()/8;
		wmParams.type = 2002;
		wmParams.format = 1;
		wmParams.flags = 40;
		wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		_floatStartButtonView.setText("Start");
		_floatStartButtonView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				_wm.removeView(_floatStartButtonView);
				createFStopButton();
				Intent intent = new Intent(MainActivity.this, botService.class);
				startService(intent);
			}
		});
		_wm.addView(_floatStartButtonView, wmParams); // �遣View
	}
	
	private void createFStopButton() {
		Display display = getWindowManager().getDefaultDisplay();
		_floatStopButtonView = new Button(getApplicationContext());
		_wm = (WindowManager) getApplicationContext()
				.getSystemService("window");
		WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
		wmParams.gravity = Gravity.TOP | Gravity.LEFT ;
		wmParams.x = display.getWidth()/2+display.getWidth()/8;
		wmParams.y = display.getHeight()/8;
		wmParams.type = 2002;
		wmParams.format = 1;
		wmParams.flags = 40;
		wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		_floatStopButtonView.setText("Stop");
		_floatStopButtonView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				_wm.removeView(_floatStopButtonView);
				createFStartButton();
				if(ConfigData.solver != null){
				    Thread moribund = ConfigData.solver;
				    ConfigData.solver = null;
				    moribund.interrupt();
				  }
				Intent intent = new Intent(MainActivity.this, botService.class);
				stopService(intent);
			}
		});
		_wm.addView(_floatStopButtonView, wmParams);
	}

}
