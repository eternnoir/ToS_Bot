package com.tos_bot.touchservice;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

import com.tos_bot.puzzleslove.solution;


public abstract class AbstractTouchService {
	private int _ballgap;
	private int _inix;
	private int _iniy;

	public void setUp(int bg, int inix, int iniy) {
		_ballgap = bg;
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
		int inix = _inix + initw * _ballgap;
		int iniy = _iniy + inith * _ballgap;
		ret.addAll(touchDown(inix, iniy));

		int nowx = inix;
		int nowy = iniy;
		// step 2. add path
		for (String p : pathsetp) {
			int pp = Integer.parseInt(p);
			touchpos pos = changePathToPos(pp);
			int passx = nowx;
			int passy = nowy;
			nowx += pos.x;
			nowy += pos.y;
			switch (pp) {
			case 0:
				ret.addAll(touchMoveX(passx, nowx, 1));
				break;
			case 1:
				ret.addAll(touchMove(passx, passy, nowx, nowy, 1));
				break;
			case 2:
				ret.addAll(touchMoveY(passy, nowy, 1));
				break;
			case 3:
				ret.addAll(touchMove(passx, passy, nowx, nowy, 1));
				break;
			case 4:
				ret.addAll(touchMoveX(passx, nowx, 1));
				break;
			case 5:
				ret.addAll(touchMove(passx, passy, nowx, nowy, 1));
				break;
			case 6:
				ret.addAll(touchMoveY(passy, nowy, 1));
				break;
			case 7:
				ret.addAll(touchMove(passx, passy, nowx, nowy, 1));
				break;
			}
		}

		ret.addAll(touchMove(nowx, nowy, nowx + 20, nowy + 20, 1));
		ret.addAll(touchUp());

		return ret;
	}

	private touchpos changePathToPos(Integer p) {
		/**
		 * 5 6 7 4 + 0 3 2 1
		 */
		touchpos ret = null;
		switch (p.intValue()) {
		case 0:
			ret = new touchpos(_ballgap, 0);
			break;
		case 1:
			ret = new touchpos(_ballgap, _ballgap);
			break;
		case 2:
			ret = new touchpos(0, _ballgap);
			break;
		case 3:
			ret = new touchpos(-_ballgap, _ballgap);
			break;
		case 4:
			ret = new touchpos(-_ballgap, 0);
			break;
		case 5:
			ret = new touchpos(-_ballgap, _ballgap);
			break;
		case 6:
			ret = new touchpos(0, -_ballgap);
			break;
		case 7:
			ret = new touchpos(_ballgap, -_ballgap);
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
