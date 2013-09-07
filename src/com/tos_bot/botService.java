package com.tos_bot;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import com.tos_bot.touchservice.AbstractTouchService;
import com.tos_bot.touchservice.touchDeviceFactory;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
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
			// check thread is alive?
			if (ConfigData.solver == null) {
				ConfigData.solver = new ServerSlove();
				ConfigData.solver.start();
			} else if (!ConfigData.solver.isAlive()) {
				ConfigData.solver = null;
			}
			handler.postDelayed(this, 2000);
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
			String url1 = ConfigData.Serverurl;
			String url2 = "board=" + board + "&deep=" + ConfigData.deep
					+ "&weight="
					+ weightMap.getInstance().getWeight(ConfigData.StyleName)
					+ "&ed=" + ConfigData.eightd;
			Log.i("Bot:", "Url: " + url1 + "?" + url2);
			showMessage("Solving");
			httpService hs = new httpService();
			String solstr = hs.httpServiceGet(url1, url2);
			if (solstr.equals("")) {
				Log.i("Bot:", "NetWorkError");
				showMessage("Network Error");
				Toast.makeText(getApplicationContext(), "Network Error",
						Toast.LENGTH_SHORT).show();
				return;
			}
			Log.i("Bot:", "ServerRet: " + solstr);
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
					Log.i("Bot:", "Touch Event Not Found");
					showMessage("Touch Event Not Found, Check Your Device");
					return;
				}
				Vector<String> cmd = ts.getCommandByPath(ih, iw, pathsetp);
				ts.SendCommand(cmd);
			} else {
				showMessage("Bot Stop");
				Log.i("Bot:", "Thread interrupt by User");
				return;
			}
			Log.i("Bot:", "Wait 12 Secs");
			showMessage("Waiting");
			SystemClock.sleep(12000);
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
				showMessage("Take Screenshot");
				os.write(("chmod 777 " + ConfigData.TempDir + "/img.png\n")
						.getBytes("ASCII"));
				os.flush();
				os.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			Log.i("Bot:", "Take ScreenShot Done");
			return true;
		}

		private void cpFile() {
			Process p;
			try {
				// Preform su to get root privledges
				p = Runtime.getRuntime().exec("su", null, null);
				DataOutputStream os = new DataOutputStream(p.getOutputStream());
				// Attempt to write a file to a root-only
				String cmd = "cp " + ConfigData.GooglePlayFp + " "
						+ ConfigData.TempDir + "/TOS_tmp.xml\n";
				os.write(cmd.getBytes());
				os.flush();
				cmd = "cp " + ConfigData.MyCardFp + " " + ConfigData.TempDir
						+ "/TOS_tmp.xml\n";
				os.write(cmd.getBytes());
				os.flush();
				cmd = "chmod 777  " + ConfigData.TempDir + "/TOS_tmp.xml\n";
				os.write(cmd.getBytes());
				os.flush();

				os.writeBytes("exit\n");
				os.flush();
				os.close();
				p.waitFor();
			} catch (Exception e) {
				// TODO Code to run in input/output exception
				// return false;
				e.printStackTrace();
			}
		}

		private void rmFile() {
			Process p;
			try {
				// Preform su to get root privledges
				p = Runtime.getRuntime().exec("su", null, null);

				// Attempt to write a file to a root-only
				DataOutputStream os = new DataOutputStream(p.getOutputStream());
				String rmcmd = "rm -Rf " + ConfigData.TempDir + "/img.png\n";
				os.write(rmcmd.getBytes());
				os.flush();
				os.writeBytes("exit\n");
				os.flush();
				os.close();
				p.waitFor();
			} catch (Exception e) {
				// TODO Code to run in input/output exception
				// return false;
				e.printStackTrace();
			}
		}

		private String getBoard() {
			// cpFile();
			String ret;
			xmlParser xmp = new xmlParser();
			String xmlres = xmp.parserXmlByID(ConfigData.TempDir
					+ "/TOS_tmp.xml", ConfigData.xmlid);
			String nowcdid = xmp.parserXmlByID(ConfigData.TempDir
					+ "/TOS_tmp.xml", ConfigData.cdid);
			Log.i("Bot:", "pascdid: " + ConfigData.pasCDid);
			Log.i("Bot:", "nowcdid: " + nowcdid);

			// check the xml file is change?
			if (nowcdid.equals(ConfigData.pasCDid)) {
				return getBoardFromPic();
			} else {
				ConfigData.pasCDid = nowcdid;
			}

			if (xmlres.equals("")) {
				return null;
			} else if (xmlres.equals("Can't Find File")) {
				return null;
			}
			String[] result = xmlres.split("_");
			ret = result[3];
			int k = 5;
			for (int i = 1; i < 30; i++) {
				ret += result[k];
				k = k + 2;
			}
			return ret;
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

		private void showMessage(final String msg) {
			final Runnable mShowMessage = new Runnable() {
			    public void run() {
			        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
			    }
			};
			mHandler.post(mShowMessage);
		}
	}
}
