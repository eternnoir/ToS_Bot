package com.tos_bot;

import com.tos_bot.board.BoardManager;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
		Button imgTestbtn = (Button) findViewById(R.id.ImgTestbutton);
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				saveSetting();
			}
		});
		imgTestbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String sdDir = "/sdcard";
				ConfigData.TempDir = sdDir;
				String result = getBoardFromPic();
				Toast.makeText(getApplicationContext(), result,
						Toast.LENGTH_SHORT).show();
				
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
	private String getBoardFromPic() {
		Log.i("Bot:", "Use Data Frome Pic");
		int[][] orbArray;
		try {
			orbArray = BoardManager.getBallArray();
		} catch (NotInTosException e) {
			Log.i("Bot:", "Can find bord Pic");
			return null;
		} catch (Exception e) {
			Log.i("Bot:", "Can find bord from Pic error");
			return null;
		}
		String board = "";
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 6; j++) {
				board = board + orbArray[i][j];
			}
		}
		return board;
	}
}
