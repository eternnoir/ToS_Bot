package com.tos_bot;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import com.tos_bot.touchservice.AbstractTouchService;
import com.tos_bot.touchservice.touchDeviceFactory;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

public class botService extends Service {
	private Handler handler = new Handler();
	final Handler mHandler = new Handler();

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		//magicat  handler.postDelayed(BotGo, 1000);
		handler.postDelayed(BotGo, 100);
		super.onStart(intent, startId);
	}

	@Override
	public void onDestroy() {
		handler.removeCallbacks(BotGo);
		super.onDestroy();
	}

	public void showMessage(final String msg) {
		Runnable mShowMessage = new Runnable() {
			public void run() {
				Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
			}
		};
		mHandler.post(mShowMessage);
	}

	private Runnable BotGo = new Runnable() {
		public void run() {
			// check thread is alive?
			if (ConfigData.solverThread == null) {
				ConfigData.solverThread = new ServerSlove();
				ConfigData.solverThread.start();
			} else if (!ConfigData.solverThread.isAlive()) {
				ConfigData.solverThread = null;
			}
			//magicat  handler.postDelayed(this, 2000);
			handler.postDelayed(this, 200);
		}
	};

	public class ServerSlove extends Thread {
		@Override
		public void run() {
			Log.i("Bot:", "Take Board");
			String board = getBoardFromPic();
			if (board == null) {
				return;
			}
			String serverUrl = ConfigData.Serverurl;
			String parameters = "board=" + board + "&deep=" + ConfigData.deep
					+ "&weight="
					+ weightMap.getInstance().getWeight(ConfigData.StyleName)
					+ "&ed=" + ConfigData.eightd
					+ "&combo=" + ComboWeightMap.getInstance().getWeight(ConfigData.MaxComboName);
			Log.i("Bot:", "Url: " + serverUrl + "?" + parameters);

			//magicat
			showMessage("Solving \nUrl: " + serverUrl + "?" + parameters);

			httpService hs = new httpService();
			String solstr = hs.httpServiceGet(serverUrl, parameters);
			if (solstr.equals("")) {
				Log.i("Bot:", "NetWorkError");
				showMessage("Network Error");
				Toast.makeText(getApplicationContext(), "Network Error",
						Toast.LENGTH_SHORT).show();
				return;
			}
			Log.i("Bot:", "ServerRet: " + solstr);

			showMessage("ServerRet: " + solstr);//magicat

			String[] recvStr = solstr.split(";");
			if (recvStr.length != 3) {
				Log.i("Bot:", "Server Error ");
				showMessage("Server Error");
				return;
			}
			String ini = recvStr[0];
			String[] inis = ini.split(",");
			int ih = Integer.parseInt(inis[0]);
			int iw = Integer.parseInt(inis[1]);
			String path = recvStr[2];
			Log.i("Bot:", "Path: " + path);
			Log.i("Bot:", "Combo: " + recvStr[1]);
			String[] pathsetp = path.split(",");
			if (!this.isInterrupted()) {
				AbstractTouchService ts = touchDeviceFactory
						.getNewTouchService(ConfigData.DeviceName);
				if (ts == null) {
					Log.i("Bot:", "Touch Event Not Found \nConfigData.DeviceName:" + ConfigData.DeviceName);
					showMessage("Touch Event Not Found \nConfigData.DeviceName:" + ConfigData.DeviceName);
					return;
				}
				Vector<String> cmd = ts.getCommandByPath(ih, iw, pathsetp);
				ts.SendCommand(cmd);
			} else {
				showMessage("Bot Stop by User");
				Log.i("Bot:", "Thread interrupt by User");
				return;
			}
			Log.i("Bot:", "Waiting : " + ConfigData.waitForStageChageTimeSec + " Secs");
			//magicat
			showMessage("Waiting : " + ConfigData.waitForStageChageTimeSec + " Secs");
			SystemClock.sleep(ConfigData.waitForStageChageTimeSec*1000);
		}

		private boolean getScreenshot() {
			Log.i("Bot:", "Take ScreenShot");
			Process sh;
			try {

				sh = Runtime.getRuntime().exec("su", null, null);
				OutputStream os = sh.getOutputStream();
				InputStream is = sh.getInputStream();
				os.write(("/system/bin/screencap -p " + ConfigData.TempDir + "/img.png\n")
						.getBytes("ASCII"));
				os.flush();
				
				String cmd = "echo -n 0\n";
				os.write(cmd.getBytes("ASCII"));
				os.flush();
				is.read();

				os.write(("chmod 777 " + ConfigData.TempDir + "/img.png\n")
						.getBytes("ASCII"));
				os.flush();
				os.close();
				

				showMessage("Taking Screenshot \n" + ConfigData.TempDir + "/img.png");
				//magicat timer for screen cap
				//startTime = System.currentTimeMillis();
				
				//magicat timer for screen cap
				//startTime = System.currentTimeMillis() - startTime;
				//showMessage("Take Screenshot Spend: " + startTime);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			Log.i("Bot:", "Take ScreenShot Done");
			return true;
		}
		private String getBoardFromPic() {
			Log.i("Bot:", "Use Data Frome Pic");
			getScreenshot();
			int[][] orbArray;
			try {
				orbArray = imageProcesser.getBallArray(imageProcesser
						.cutBallReg(ConfigData.TempDir + "/img.png"));
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


}
