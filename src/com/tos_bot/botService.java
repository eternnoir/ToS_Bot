package com.tos_bot;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.Vector;

import com.tos_bot.touchservice.AbstractTouchService;
import com.tos_bot.touchservice.touchDeviceFactory;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
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
			//check thread is alive?
			if(ConfigData.solver == null){
				ConfigData.solver = new ServerSlove();
				ConfigData.solver.start();
			}else if(!ConfigData.solver.isAlive()) {
				ConfigData.solver=null;
				handler.postDelayed(this, 6000);
				return;
			}
			// retry in 1 sec
			handler.postDelayed(this, 1000);
		}
	};
}
