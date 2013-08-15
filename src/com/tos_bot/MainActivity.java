package com.tos_bot;

import java.util.ArrayList;

import puzzleslove.puzzleSolver;
import puzzleslove.solution;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	private Button _startServiceButton;
	private Button _stopServiceButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		_startServiceButton = (Button) findViewById(R.id.start_button);
		_stopServiceButton = (Button) findViewById(R.id.stop_button);
		Button testb = (Button) findViewById(R.id.test);

		_startServiceButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, botService.class);
				startService(intent);
				
			}
		});
		
		_stopServiceButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, botService.class);
				stopService(intent);
				
			}
		});
		testb.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				int[][] board = {{1,1,1,3,0,3},
									{0,1,0,1,0,1},
									{3,4,2,3,3,0},
									{4,2,1,3,0,0},
									{5,2,1,3,3,4}};
				/*int[][] board = {{1,1,1,3,0,3},
									{0,4,0,1,0,1},
									{3,2,1,3,3,0},
									{4,2,1,3,0,0},
									{5,2,1,3,3,4}};*/
				puzzleSolver ps = new puzzleSolver(3, 25, 2,4,3);
				solution re = ps.solve_board(board);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
