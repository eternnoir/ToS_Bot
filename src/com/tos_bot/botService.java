package com.tos_bot;


import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.util.Vector;


import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;



public class botService extends Service {
	private Handler handler = new Handler();
	final String _filePath = "/data/data/com.madhead.tos.zh/shared_prefs/com.madhead.tos.zh.xml";
	final String _MyCardFp = "/data/data/com.madhead.tos.zh.ex/shared_prefs/com.madhead.tos.zh.ex.xml";
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
			touchService.commandDone = false;
			Log.i("Bot:", "Take Pic");
			getScreenshot();
			int[][] orbArray;
			try {
				//Toast.makeText(getApplicationContext(), "Solving..", Toast.LENGTH_SHORT).show();
				orbArray = imageProcesser.getBallArray(imageProcesser.cutBallReg("/sdcard/tmp/img.png"));
			} catch (NotInTosException e) {
				Log.i("Bot:", "pic Error");
				handler.postDelayed(this, 5000);
				return;
			}
			String mass = "";
			for(int i=0;i<5;i++){
				for(int j=0;j<6;j++){
					mass = mass+orbArray[i][j];
				}
			}

			final String url1 = ConfigData.Serverurl;
			final String url2 = "board="+mass+"&deep="+ConfigData.deep;
			Thread solver =new Thread()
			{
			    @Override
			    public void run()
			    {
					httpService hs = new httpService();
					solstr = hs.httpServiceGet(url1, url2);
					if(solstr.equals("")){
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
		 			touchService.set(270, 135, 1380);
					Vector<String> cmd = touchService.getCommandByPath(ih,iw,pathsetp);
					//Toast.makeText(getApplicationContext(), "Solving..", Toast.LENGTH_SHORT).show();
					touchService.SendCommand(cmd);
					touchService.commandDone =true;
			    }
			};
			solver.start();
			while(solver.isAlive()){
				//Log.i("Bot:", "Wait For Solving");
			}
			handler.postDelayed(this, 12000);
			
			/*
			String[] pathsetp = path.split(",");
 			touchService.set(270, 135, 1380);
			Vector<String> cmd = touchService.getCommandByPath(pathsetp);
			touchService.SendCommand(cmd);
			handler.postDelayed(this, 10000);
			/*
			puzzleSolver ps = new puzzleSolver(2,30,2,4);
			solution re = ps.solve_board(orbArray);
			
			//Toast.makeText(getApplicationContext(), "Send path", Toast.LENGTH_SHORT).show();
 			touchService.set(270, 135, 1380);
			Vector<String> cmd = touchService.getCommandBySol(re);
			touchService.SendCommand(cmd);
			handler.postDelayed(this, 10000);
			*/
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
		Toast.makeText(getApplicationContext(), "Capture Screen Done", Toast.LENGTH_SHORT).show();
		return true;
	}
	
	private void checkoutRoot() {
		Process p;
		try {
			// Preform su to get root privledges
			p = Runtime.getRuntime().exec("su");

			// Attempt to write a file to a root-only
			DataOutputStream os = new DataOutputStream(p.getOutputStream());
			String mkdircmd = "mkdir /mnt/sdcard/tmp\n";
			os.writeBytes(mkdircmd);
			
			String cmd = "cp " + _filePath + " /mnt/sdcard/tmp/TOS_tmp.xml\n";
			os.writeBytes(cmd);
			cmd = "cp " + _MyCardFp + " /mnt/sdcard/tmp/TOS_tmp.xml\n";
			os.writeBytes(cmd);

			// Close the terminal
			os.writeBytes("exit\n");
			os.flush();
			try {
				p.waitFor();
				if (p.exitValue() != 255) {
					// TODO Code to run on success
					// return true;
				} else {
					// TODO Code to run on unsuccessful
					// return false;
				}
			} catch (InterruptedException e) {
				// TODO Code to run in interrupted exception
				// return false;
			}
		} catch (IOException e) {
			// TODO Code to run in input/output exception
			// return false;
		}
	}

}
