package com.tos_bot;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GeneralSettingActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_generalsetting);
		EditText su = (EditText) findViewById(R.id.ServerUrl);
		EditText deep = (EditText) findViewById(R.id.Deep);
		EditText maxcombo = (EditText) findViewById(R.id.MaxCombo);
		EditText gap = (EditText) findViewById(R.id.GapText);
		SharedPreferences settings = getSharedPreferences("Config", 0);
		su.setText(settings.getString("Serverurl", "http://tbserver.ap01.aws.af.cm/"));
		deep.setText(settings.getInt("deep", 30)+"");
		maxcombo.setText(settings.getString("maxBombo", "0"));
		gap.setText(settings.getString("maxBombo", "70"));
		
		Button save = (Button) findViewById(R.id.SaveBtn);
		save.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				saveSetting();
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

	private void saveSetting() {
		SharedPreferences settings = getSharedPreferences("Config", 0);
		settings.edit().putString("Serverurl",
						((EditText) findViewById(R.id.ServerUrl)).getText()
								.toString()).commit();
		settings.edit().putInt("deep",
						Integer.parseInt(((EditText) findViewById(R.id.Deep))
								.getText().toString())).commit();
		settings.edit().putString(
						"maxBombo",
						((EditText) findViewById(R.id.MaxCombo)).getText()
								.toString()).commit();
		settings.edit().putString(
				"gap",
				((EditText) findViewById(R.id.GapText)).getText()
						.toString()).commit();

	}
}
