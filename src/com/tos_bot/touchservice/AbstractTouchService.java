package com.tos_bot.touchservice;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import java.util.Vector;
import android.util.Log;

import android.os.SystemClock;

import com.tos_bot.ConfigData;
import com.tos_bot.puzzleslove.solution;


public abstract class AbstractTouchService {
	private int _ballgap;
	private int _ballgapRandom;
	private int _inix;
	private int _iniy;
	Random RandomTime = new Random(); //設定轉珠速度亂數
	//int RandomTime=R.nextInt(20-3)+1;//設定轉珠速度亂數範圍

	public void setUp(int ballgap, int inix, int iniy) {
		_ballgap = ballgap;
		_inix = inix;
		_iniy = iniy;
	}

	public void SendCommand(Vector<String> str) {
		Process sh;
		try {
			sh = Runtime.getRuntime().exec("su", null, null);
			OutputStream os = sh.getOutputStream();
			for (String s : str) {
				os.write(s.getBytes("ASCII"));
				os.flush();
			}
			os.close();
			sh.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	public Vector<String> getCommandBySol(solution s) {

		Vector<String> ret = new Vector<String>();
		// step 1. get init
		int inix = _inix + s.initcursor.w * _ballgap;
		int iniy = _iniy + s.initcursor.h * _ballgap;
		ret.addAll(touchDown(inix, iniy));

		int nowx = inix;
		int nowy = iniy;
		// step 2. add path
		for (Integer p : s.path) {
			touchpos pos = changePathToPos(p);
			int passx = nowx;
			int passy = nowy;
			nowx += pos.x;
			nowy += pos.y;
			ret.addAll(touchMove(passx, passy, nowx, nowy, 1));
		}
		ret.addAll(touchUp());

		return ret;
	}

	public Vector<String> getCommandByPath(int inith, int initw,
			String[] pathsetp) {

		Vector<String> ret = new Vector<String>();
		// step 1. get init
		int nowx; 
		int nowy;
		if (ConfigData.DeviceName.equals("P85")){
			nowx = _inix + inith * _ballgap;//上下相反_inix 435, _iniy705
			nowy = _iniy - initw * _ballgap;
		}else{
			nowx = _inix + initw * _ballgap;
			nowy = _iniy + inith * _ballgap;
		}

		ret.addAll(touchDown(nowx, nowy));
		SystemClock.sleep(50); //default
		// step 2. add path
		
		int gap = 70;//移動速度間隔 default int gap = 70
		
		for (String p : pathsetp) {
			gap = RandomTime.nextInt(2)>=1?120:40;//設定轉珠速度亂數範圍
			//Log.e("RandomTime.nextInt(4):",""+RandomTime.nextInt(2));
			//Log.e("RandomTime Gap:",""+gap);
			int pp = Integer.parseInt(p);
			touchpos pos = changePathToPos(pp);
			int passx = nowx;//原座標
			int passy = nowy;//原座標
			if (ConfigData.DeviceName.equals("P85")){
				nowx += pos.x;//上下相反
				nowy += pos.y;
			}else{
				nowx += pos.x;
				nowy += pos.y;
			}
			switch (pp) { //0右 2下 4左 6上
			case 0:
				ret.addAll(touchMoveX(passx, nowx, gap));
				break;
			case 1:
				ret.addAll(touchMove(passx, passy, nowx, nowy, gap));
				break;
			case 2:
				ret.addAll(touchMoveY(passy, nowy, gap));
				break;
			case 3:
				ret.addAll(touchMove(passx, passy, nowx, nowy, gap));
				break;
			case 4:
				ret.addAll(touchMoveX(passx, nowx, gap));
				break;
			case 5:
				ret.addAll(touchMove(passx, passy, nowx, nowy,gap ));
				break;
			case 6:
				ret.addAll(touchMoveY(passy, nowy, gap));
				break;
			case 7:
				ret.addAll(touchMove(passx, passy, nowx, nowy, gap));
				break;
			}
		
		}
		//touchMove(nowx, nowy, nowx, nowy+5, gap);
		SystemClock.sleep(100);//default 100 
		ret.addAll(touchUp());

		return ret;
	}

	private touchpos changePathToPos(Integer p) {
		/**
		 * 5 6 7 4 + 0 3 2 1
		 */
		_ballgapRandom = _ballgap ;//設定轉珠路徑亂數範圍 + RandomTime.nextInt(_ballgap/10)
		touchpos ret = null;
		switch (p.intValue()) {
		case 0:
			ret = new touchpos(_ballgapRandom, 0);
			break;
		case 1:
			ret = new touchpos(_ballgapRandom, _ballgapRandom);
			break;
		case 2:
			ret = new touchpos(0, _ballgapRandom);
			break;
		case 3:
			ret = new touchpos(-_ballgapRandom, _ballgapRandom);
			break;
		case 4:
			ret = new touchpos(-_ballgapRandom, 0);
			break;
		case 5:
			ret = new touchpos(-_ballgapRandom, -_ballgapRandom);
			break;
		case 6:
			ret = new touchpos(0, -_ballgapRandom);
			break;
		case 7:
			ret = new touchpos(_ballgapRandom, -_ballgapRandom);
			break;
		}
		return ret;
	}

	public abstract Vector<String> touchDown(int x, int y);

	public abstract Vector<String> touchUp();

	public abstract Vector<String> touchMove(int x1, int y1, int x2, int y2,
			int gap);

	public abstract Vector<String> touchMoveX(int x1, int x2, int gap);

	public abstract Vector<String> touchMoveY(int y1, int y2, int gap);

}
