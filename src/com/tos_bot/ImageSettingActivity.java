package com.tos_bot;

import com.tos_bot.board.BoardManager;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ImageSettingActivity extends Activity {
	private EditText posX;
	private EditText posY;
	private EditText oneOrbWitdh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setimage);
		posX = (EditText) findViewById(R.id.BoardPosX);
		posY = (EditText) findViewById(R.id.BoardPosY);
		oneOrbWitdh = (EditText) findViewById(R.id.OrbWitdthText);
		Button save = (Button) findViewById(R.id.ImgSSave);
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				saveSetting();
			}
		});
		loadSetting();
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

	private void loadSetting() {
		SharedPreferences settings = getSharedPreferences("Config", 0);
		oneOrbWitdh.setText(settings.getInt("oneOrbWitdh", 0)+"");
		posX.setText(settings.getInt("boardStartX", 0)+"");
		posY.setText(settings.getInt("boardStartY", 0)+"");

	}

	private void saveSetting() {
		SharedPreferences settings = getSharedPreferences("Config", 0);
		settings.edit()
				.putInt("oneOrbWitdh",
						Integer.parseInt(oneOrbWitdh.getText().toString()))
				.commit();
		settings.edit()
				.putInt("boardStartX",
						Integer.parseInt(posX.getText().toString())).commit();
		settings.edit()
				.putInt("boardStartY",
						Integer.parseInt(posY.getText().toString())).commit();

	}
}
