package com.tos_bot;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.Vector;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class botService extends Service {
	private Handler handler = new Handler();
	final String _filePath = "/data/data/com.madhead.tos.zh/shared_prefs/com.madhead.tos.zh.xml";
	final String _MyCardFp = "/data/data/com.madhead.tos.zh.ex/shared_prefs/com.madhead.tos.zh.ex.xml";
	private final String xmlid = "MH_CACHE_GAMEPLAY_DATA_ALL";
	private final String cdid = "MH_CACHE_GAMEPLAY_DATA_DECREASE_CD";

	String solstr;

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
			Log.i("Bot:", "Take Pic");
			cpFile();
			Thread solver = new Thread() {
				@Override
				public void run() {
					String board = getBoard();
					if (board == null) {
						return;
					}

					final String url1 = ConfigData.Serverurl;
					final String url2 = "board=" + board + "&deep=" + ConfigData.deep;
					httpService hs = new httpService();
					solstr = hs.httpServiceGet(url1, url2);
					if (solstr.equals("")) {
						Log.i("Bot:", "NetWorkError");
						handler.postDelayed(this, 3000);
						return;
					}
					String[] recvStr = solstr.split(";");
					String ini = recvStr[0];
					String[] inis = ini.split(",");
					int ih = Integer.parseInt(inis[0]);
					int iw = Integer.parseInt(inis[1]);
					String path = recvStr[2];
					String[] pathsetp = path.split(",");
					touchService.set(270, 135, 1380);
					Vector<String> cmd = touchService.getCommandByPath(ih, iw,
							pathsetp);
					// Toast.makeText(getApplicationContext(), "Solving..",
					// Toast.LENGTH_SHORT).show();
					touchService.SendCommand(cmd);
					touchService.commandDone = true;
				}
			};
			solver.start();
			while (solver.isAlive()) {
				// Log.i("Bot:", "Wait For Solving");
			};
			handler.postDelayed(this, 12000);
			/*
			 * String[] pathsetp = path.split(","); touchService.set(270, 135,
			 * 1380); Vector<String> cmd =
			 * touchService.getCommandByPath(pathsetp);
			 * touchService.SendCommand(cmd); handler.postDelayed(this, 10000);
			 * /* puzzleSolver ps = new puzzleSolver(2,30,2,4); solution re =
			 * ps.solve_board(orbArray);
			 * 
			 * //Toast.makeText(getApplicationContext(), "Send path",
			 * Toast.LENGTH_SHORT).show(); touchService.set(270, 135, 1380);
			 * Vector<String> cmd = touchService.getCommandBySol(re);
			 * touchService.SendCommand(cmd); handler.postDelayed(this, 10000);
			 */
		}
	};

	private boolean getScreenshot() {

		Process sh;
		try {
			sh = Runtime.getRuntime().exec("su", null, null);
			OutputStream os = sh.getOutputStream();
			os.write(("/system/bin/screencap -p " + "/sdcard/tmp/img.png")
					.getBytes("ASCII"));
			os.flush();

			os.close();
			sh.waitFor();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;
	}

	private void cpFile() {
		Process p;
		try {
			// Preform su to get root privledges
			p = Runtime.getRuntime().exec("su", null, null);

			// Attempt to write a file to a root-only
			DataOutputStream os = new DataOutputStream(p.getOutputStream());
			String mkdircmd = "mkdir /mnt/sdcard/tmp\n";
			os.writeBytes(mkdircmd);
			String rmcmd = "rm -rf /mnt/sdcard/tmp/TOS_tmp.xml\n";
			os.writeBytes(rmcmd);
			String cmd = "cat " + _filePath + ">> /mnt/sdcard/tmp/TOS_tmp.xml";
			os.writeBytes(cmd);
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
		//cpFile();
		String ret;
		xmlParser xmp = new xmlParser();
		String xmlres = xmp.parserXmlByID("/mnt/sdcard/tmp/TOS_tmp.xml", xmlid);
		String nowcdid = xmp.parserXmlByID("/mnt/sdcard/tmp/TOS_tmp.xml", cdid);
		Log.i("Bot:", touchService.pasCDid);
		Log.i("Bot:", nowcdid);

		if (nowcdid.equals(touchService.pasCDid)) {
			if(touchService.delay >5){
				touchService.pasCDid ="";	//over 5 times reset
			}else{
				touchService.delay++;
			}
			return null;
		} else {
			touchService.delay =0;
			touchService.pasCDid = nowcdid;
		}

		if (xmlres.equals("")) {
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

}
