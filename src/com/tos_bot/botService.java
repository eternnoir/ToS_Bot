package com.tos_bot;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import puzzleslove.puzzleSolver;
import puzzleslove.solution;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class botService extends Service {
	private Handler handler = new Handler();

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		handler.postDelayed(BotGo, 5000);
		super.onStart(intent, startId);
	}

	@Override
	public void onDestroy() {
		handler.removeCallbacks(BotGo);
		super.onDestroy();
	}

	private Runnable BotGo = new Runnable() {
		public void run() {
			//Toast.makeText(getApplicationContext(), "Bot Start", Toast.LENGTH_SHORT).show();
			getScreenshot();
			int[][] orbArray;
			imageProcesser.cutBallReg("/sdcard/tmp/img.png");
			try {
				//Toast.makeText(getApplicationContext(), "Solving..", Toast.LENGTH_SHORT).show();
				orbArray = imageProcesser.getBallArray();
			} catch (NotInTosException e) {
				// Not In ToS
				//Toast.makeText(getApplicationContext(), "Not in Tos?", 1).show();
				handler.postDelayed(this, 100);
				e.printStackTrace();
				return;
			}
			int[] pre =new int[6];
			for(int i=0;i<5;i++){
				for(int j=0;j<6;j++){
					pre[orbArray[i][j]]++;
				}
			}
			int ppre = 0;
			int tmp=0;
			for(int i=0;i<6;i++){
				if(pre[i]>tmp){
					tmp = pre[i];
					ppre = i;
				}
			}
			puzzleSolver ps = new puzzleSolver(3,6,2,3,ppre);
			ArrayList<solution> re = ps.solve_board(orbArray);
			if(re.size()==0){
				Toast.makeText(getApplicationContext(), "No Result", Toast.LENGTH_SHORT).show();
				return;
			}
			//Toast.makeText(getApplicationContext(), "Send path", Toast.LENGTH_SHORT).show();
			touchService.set(270, 135, 1380);
			Vector<String> cmd = touchService.getCommandBySol(re.get(0));
			touchService.SendCommand(cmd);
			handler.postDelayed(this, 100);
			
		}
	};
	
	private boolean getScreenshot(){
		Process sh;
		try {
			sh = Runtime.getRuntime().exec("su", null, null);
			OutputStream os = sh.getOutputStream();
			os.write(("/system/bin/screencap -p " + "/sdcard/tmp/img.png")
					.getBytes("ASCII"));
			os.flush();

			os.close();
			sh.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
