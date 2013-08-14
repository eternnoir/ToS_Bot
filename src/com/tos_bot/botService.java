package com.tos_bot;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class botService extends Service {
	private Handler handler = new Handler();

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		handler.postDelayed(BotGo, 1000);
		super.onStart(intent, startId);
	}

	@Override
	public void onDestroy() {
		handler.removeCallbacks(BotGo);
		super.onDestroy();
	}

	private Runnable BotGo = new Runnable() {
		public void run() {
			// log目前時間
			Log.i("time:", new Date().toString());
			handler.postDelayed(this, 5000);
			getScreenshot();
			//imageProcesser.cutBallReg("/sdcard/tmp/img.png");
			//imageProcesser.getBallArray();
			touchService.myClickEvent(11, 11);
			
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
