package com.tos_bot;

import com.tos_bot.touchservice.touchDeviceFactory;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Button _startServiceButton;
	private Button _stopServiceButton;
	private Spinner _deviceS;
	private Spinner _styleList;
	private Button _floatStartButtonView = null;
	private Button _floatStopButtonView = null;
    private Button _floatStrategyButtonView = null;
    private LinearLayout _floatStrategyLayout = null;
	private WindowManager _wm = null;
    private Boolean _isStartButtonExist = false;
    private Boolean _isStopButtonExist = false;
    private Boolean _isStrategyButtonExist = false;
    private Boolean _isStrategyHorizontalScrollViewExist = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.initDeviceList(); // create device list
		this.initStyleList();
		ConfigData.TempDir = getCacheDir() + "";
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
				CheckBox edcheck =  (CheckBox) findViewById(R.id.eightDircheck);
				ConfigData.Serverurl = serveret.getText().toString();
				ConfigData.deep = Integer.parseInt(deepet.getText().toString());
				ConfigData.DeviceName = _deviceS.getSelectedItem().toString();
				ConfigData.StyleName = _styleList.getSelectedItem().toString();
				if(edcheck.isChecked()){
					ConfigData.eightd = 1;
				}else{
					ConfigData.eightd = 0;
				}
				if (_floatStartButtonView == null) {
					createFStartButton();
				}

                if (_floatStrategyButtonView == null) {
                    createFStrategyButton();
                }
			}
		});

		_stopServiceButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ConfigData.pasCDid = "";
				try {
                    if (_isStartButtonExist)
                        _wm.removeView(_floatStartButtonView);
                    if (_isStopButtonExist)
					    _wm.removeView(_floatStopButtonView);
                    if (_isStrategyButtonExist)
                        _wm.removeView(_floatStrategyButtonView);
                    if (_isStrategyHorizontalScrollViewExist)
                        _wm.removeView(_floatStrategyLayout);
                    _isStartButtonExist = false;
                    _isStopButtonExist = false;
                    _isStrategyButtonExist = false;
                    _isStrategyHorizontalScrollViewExist = false;
                } catch (Exception e) {
					// TODO: handle exception
				}
				_floatStartButtonView = null;
				_floatStopButtonView = null;
                _floatStrategyButtonView = null;
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
				android.R.layout.simple_spinner_item, weightMap.getInstance()
						.getStyleList());
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}

	private void createFStartButton() {
		Display display = getWindowManager().getDefaultDisplay();
		_floatStartButtonView = new Button(getApplicationContext());
		_wm = (WindowManager) getApplicationContext()
				.getSystemService("window");
		WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
		wmParams.gravity = Gravity.TOP | Gravity.LEFT;
		wmParams.x = 0 + display.getWidth() / 8;
		wmParams.y = display.getHeight() / 8;
		wmParams.type = 2002;
		wmParams.format = 1;
		wmParams.flags = 40;
		wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		_floatStartButtonView.setText("Start");
		_floatStartButtonView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				_wm.removeView(_floatStartButtonView);
                _isStartButtonExist = false;
                _wm.removeView(_floatStrategyButtonView);
                _isStrategyButtonExist = false;
				createFStopButton();
				Intent intent = new Intent(MainActivity.this, botService.class);
				startService(intent);
			}
		});
		_wm.addView(_floatStartButtonView, wmParams); // ?遣View
        _isStartButtonExist = true;
	}

	private void createFStopButton() {
		Display display = getWindowManager().getDefaultDisplay();
		_floatStopButtonView = new Button(getApplicationContext());
		_wm = (WindowManager) getApplicationContext()
				.getSystemService("window");
		WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
		wmParams.gravity = Gravity.TOP | Gravity.LEFT;
		wmParams.x = display.getWidth() / 2 + display.getWidth() / 8;
		wmParams.y = display.getHeight() / 8;
		wmParams.type = 2002;
		wmParams.format = 1;
		wmParams.flags = 40;
		wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		_floatStopButtonView.setText("Stop");
		_floatStopButtonView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				_wm.removeView(_floatStopButtonView);
                _isStopButtonExist = false;
				createFStartButton();
                createFStrategyButton();
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
        _isStopButtonExist = true;
	}

    private void createFStrategyButton() {
        Display display = getWindowManager().getDefaultDisplay();
        _floatStrategyButtonView = new Button(getApplicationContext());
        _wm = (WindowManager) getApplicationContext()
                .getSystemService("window");
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        wmParams.gravity = Gravity.TOP | Gravity.LEFT;
        wmParams.x = 0 + display.getWidth() / 8;
        wmParams.y = display.getHeight() * 2 / 8;
        wmParams.type = 2002;
        wmParams.format = 1;
        wmParams.flags = 40;
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        _floatStrategyButtonView.setText("Strategy");
        _floatStrategyButtonView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //_wm.removeView(_floatStrategyButtonView);
                createFStrategyHorizontalScrollView();
            }
        });
        _wm.addView(_floatStrategyButtonView, wmParams); // ?遣View
        _isStrategyButtonExist = true;
    }

    private void createFStrategyHorizontalScrollView() {
        _floatStrategyLayout = new LinearLayout(getApplicationContext());
        _wm = (WindowManager) getApplicationContext()
                .getSystemService("window");
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        wmParams.gravity = Gravity.TOP | Gravity.LEFT;
        wmParams.x = 0;
        wmParams.y = 0;
        wmParams.type = 2002;
        wmParams.format = 1;
        wmParams.flags = 40;
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        HorizontalScrollView scrollView = new HorizontalScrollView(this);
        scrollView.addView(getStrategyLinearLayout());
        _floatStrategyLayout.addView(scrollView);
        _wm.addView(_floatStrategyLayout, wmParams); // ?遣View
        _isStrategyHorizontalScrollViewExist = true;
        _floatStrategyButtonView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                _wm.removeView(_floatStrategyButtonView);
            }
        });
    }

    private LinearLayout getStrategyLinearLayout(){
        LinearLayout layout = new LinearLayout(this);
        String[] styleList = weightMap.getInstance().getStyleList();

        for (int i = 0; i < styleList.length; i++){
            layout.addView(getImageButton(styleList[i]));
        }

        return layout;
    }

    private ImageButton getImageButton(String styleName){
        ImageButton button = new ImageButton(this);
        button.getBackground().setAlpha(0);
        button.setImageBitmap(BitmapFactory.decodeStream(getClassLoader().getResourceAsStream("image/" + styleName + ".png")));
        return button;
    }

}
