package com.tos_bot;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.Vector;

import android.os.Environment;
import android.util.Log;

import com.tos_bot.touchservice.AbstractTouchService;
import com.tos_bot.touchservice.touchDeviceFactory;

public class ServerSlove extends Thread {
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
						.cutBallReg(ConfigData.TempDir+"/img.png"));
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
		String solstr = hs.httpServiceGet(url1, url2);
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
	private boolean getScreenshot() {

		Process sh;
		try {
			sh = Runtime.getRuntime().exec("su", null, null);
			OutputStream os = sh.getOutputStream();
			os.write(("/system/bin/screencap -p "+ConfigData.TempDir+"/img.png\n")
					.getBytes("ASCII"));
			os.flush();
			os.write(("chmod 777 "+ConfigData.TempDir+"/img.png\n")
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
			String cmd = "cp " + ConfigData.GooglePlayFp + " "
					+ ConfigData.TempDir
					+ "/TOS_tmp.xml\n";
			os.write(cmd.getBytes());
			os.flush();
			cmd = "cp " + ConfigData.MyCardFp + " "
					+ ConfigData.TempDir
					+ "/TOS_tmp.xml\n";
			os.write(cmd.getBytes());
			os.flush();
			cmd = "chmod 777  "+ ConfigData.TempDir
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
				ConfigData.TempDir
				+ "/TOS_tmp.xml",
				ConfigData.xmlid);
		String nowcdid = xmp.parserXmlByID(
				ConfigData.TempDir
				+ "/TOS_tmp.xml",
				ConfigData.cdid);
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
