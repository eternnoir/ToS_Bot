package com.tos_bot;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.Vector;

import touchservice.AbstractTouchService;
import touchservice.touchDeviceFactory;
import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

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
			if(ConfigData.solver == null){
				setSolverThread();
				ConfigData.solver.start();
			}else if(!ConfigData.solver.isAlive()) {
				ConfigData.solver=null;
				handler.postDelayed(this, 6000);
				return;
			}
			handler.postDelayed(this, 1000);

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
	private void setSolverThread(){
		ConfigData.solver = new Thread() {
			@Override
			public void run() {
				Log.i("Bot:", "Take Board");
				cpFile();
				String board = getBoard();	//get board data from file
				if (board == null) {	//get board data from image
					Log.i("Bot:", "Use Data Frome Pic");
					getScreenshot();
					int[][] orbArray;
					try {
						orbArray = imageProcesser.getBallArray(imageProcesser
								.cutBallReg(getCacheDir()+"/img.png"));
					} catch (NotInTosException e) {
						Log.i("Bot:", "Can find bord Pic");
						return;
					}	catch (Exception e) {
						Log.i("Bot:", "Can find bord from Pic error");
						return;
					}
					board = "";
					for (int i = 0; i < 5; i++) {
						for (int j = 0; j < 6; j++) {
							board = board + orbArray[i][j];
						}
					}
				}
				String url1 = ConfigData.Serverurl;
				String url2 = "board=" + board + "&deep=" + ConfigData.deep + "&weight=" + weightMap.getInstance().getWeight(ConfigData.StyleName);

				httpService hs = new httpService();
				solstr = hs.httpServiceGet(url1, url2);
				if (solstr.equals("")) {
					Log.i("Bot:", "NetWorkError");
					return;
				}
				String[] recvStr = solstr.split(";");
				String ini = recvStr[0];
				String[] inis = ini.split(",");
				int ih = Integer.parseInt(inis[0]);
				int iw = Integer.parseInt(inis[1]);
				String path = recvStr[2];
				String[] pathsetp = path.split(",");
				AbstractTouchService ts = touchDeviceFactory
						.getNewTouchService(ConfigData.DeviceName);
				assert (ts != null);
				Vector<String> cmd = ts.getCommandByPath(ih, iw, pathsetp);
				ts.SendCommand(cmd);
			}
		};
	}

	private boolean getScreenshot() {

		Process sh;
		try {
			sh = Runtime.getRuntime().exec("su", null, null);
			OutputStream os = sh.getOutputStream();
			os.write(("/system/bin/screencap -p "+getCacheDir()+"/img.png\n")
					.getBytes("ASCII"));
			os.flush();
			os.write(("chmod 777 "+getCacheDir()+"/img.png\n")
					.getBytes("ASCII"));
			os.flush();
			os.write(("exit\n").getBytes("ASCII"));
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
			DataOutputStream os = new DataOutputStream(p.getOutputStream());
			// Attempt to write a file to a root-only
			String cmd = "cp " + _filePath + " "
					+ getCacheDir()
					+ "/TOS_tmp.xml\n";
			os.write(cmd.getBytes());
			os.flush();
			cmd = "cp " + _MyCardFp + " "
					+ getCacheDir()
					+ "/TOS_tmp.xml\n";
			os.write(cmd.getBytes());
			os.flush();
			cmd = "chmod 777  "+ getCacheDir()
					+ "/TOS_tmp.xml\n";
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
			String rmcmd = "rm -Rf "
					+ Environment.getExternalStorageDirectory()
					+ "/TOS_tmp.xml\n";
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
		String xmlres = xmp.parserXmlByID(
				getCacheDir()
				+ "/TOS_tmp.xml",
				xmlid);
		String nowcdid = xmp.parserXmlByID(
				getCacheDir()
				+ "/TOS_tmp.xml",
				cdid);
		Log.i("Bot:", "pascdid: " + ConfigData.pasCDid);
		Log.i("Bot:", "nowcdid: " + nowcdid);

		if (nowcdid.equals(ConfigData.pasCDid)) {
			return null;
		} else {
			ConfigData.pasCDid = nowcdid;
		}

		if (xmlres.equals("")) {
			return null;
		}else if(xmlres.equals("Can't Find File")){
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
