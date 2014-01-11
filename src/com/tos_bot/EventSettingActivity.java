/**
 * 
 */
package com.tos_bot;

import net.atec.analyzer.Analizer;
import net.atec.sender.DeviceEvent;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author frankwang
 *
 */
public class EventSettingActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setevent);
		Button auto = (Button) findViewById(R.id.Autoana);
		auto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				autoAna();
			}
		});
	}
	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void autoAna(){
		Analizer an = new Analizer();
		DeviceEvent de;
		try {
			an.setFileDir(ConfigData.TempDir);
			de = an.getDeviceEvent();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		((EditText)findViewById(R.id.deviceNameEditText)).setText("Auto");
		((EditText)findViewById(R.id.TouchEventID)).setText(de.getEvent());
		((EditText)findViewById(R.id.PosXId)).setText(de.getXid()+"");
		((EditText)findViewById(R.id.PosYId)).setText(de.getYid()+"");
		((EditText)findViewById(R.id.PosXMax)).setText(de.getScreenXMax());
		((EditText)findViewById(R.id.PosYMax)).setText(de.getScreenYMax());
		((EditText)findViewById(R.id.TrackingID)).setText(de.getTrackingIDid()+"");
		((EditText)findViewById(R.id.PressureID)).setText(de.getPurssureid()+"");
		int width = Integer.parseInt(de.getScreenXMax());
		int oneball = width / 6;
		int heigh = Integer.parseInt(de.getScreenYMax());
		String startXpos = (oneball / 2)+"";
		String startYpos = ((int) (heigh * 0.45)+ (oneball / 2))+"";
		((EditText)findViewById(R.id.OneBallMove)).setText(oneball+"");
		((EditText)findViewById(R.id.StartPosX)).setText(startXpos);
		((EditText)findViewById(R.id.StartPosY)).setText(startYpos);
	}
	private void saveSetting(){
		SharedPreferences settings = getSharedPreferences("Config", 0);
		settings.edit().putString("touchEventNum", ((EditText)findViewById(R.id.deviceNameEditText)).getText().toString());
	}
}
