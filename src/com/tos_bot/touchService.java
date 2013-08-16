package com.tos_bot;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

import puzzleslove.solution;

import android.os.SystemClock;
import android.view.MotionEvent;

public class touchService {
	
	static int _ballgap;
	static int _inix;
	static int _iniy;
	public static boolean commandDone;
	public static void set(int bg,int inix,int iniy){
		_ballgap = bg;
		_inix = inix;
		_iniy = iniy;
	}

	public static void SendCommand(Vector<String> str) {
		Process sh;
		try {
			sh = Runtime.getRuntime().exec("su", null, null);
			OutputStream os = sh.getOutputStream();
			for(String s:str){
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
	
	public static Vector<String> touchDown(int x,int y){
		Vector<String> cl = new Vector<String>();
		cl.add("sendevent /dev/input/event3 3 53 "+x+"\n");
		cl.add("sendevent /dev/input/event3 3 54 "+y+"\n");
		cl.add("sendevent /dev/input/event3 3 57 105\n");
		cl.add("sendevent /dev/input/event3 0 0 0\n");
		return cl;
	}
	public static Vector<String> touchUp(){
		Vector<String> cl = new Vector<String>();
		cl.add("sendevent /dev/input/event3 3 57 4294967295\n");
		cl.add("sendevent /dev/input/event3 0 0 0 \n");
		return cl;
	}
	public static Vector<String> touchMove(int x1, int y1, int x2, int y2, int gap){
		Vector<String> cl = new Vector<String>();
		int x=x1;
		int y=y1;
		int xGap = (x2-x1)/gap;
		int yGap = (y2-y1)/gap;
		for(int g=0;g<gap;g++){
			x=x1+g*xGap;
			y=y1+g*yGap;
			// send x command
			cl.add("sendevent /dev/input/event3 3 53 "+ x +"\n");
			cl.add("sendevent /dev/input/event3 0 0 0 \n");
			//send y command
			cl.add("sendevent /dev/input/event3 3 54 "+ y +"\n");
			cl.add("sendevent /dev/input/event3 0 0 0 \n");
			
		}
		return cl;
	}
	
	public static Vector<String> touchMoveX(int x1,  int x2,  int gap){
		Vector<String> cl = new Vector<String>();
		int x=x1;

		int xGap = (x2-x1)/gap;

		for(int g=0;g<gap;g++){
			x=x1+g*xGap;
			// send x command
			cl.add("sendevent /dev/input/event3 3 53 "+ x +"\n");
			cl.add("sendevent /dev/input/event3 0 0 0 \n");
			
		}
		return cl;
	}
	public static Vector<String> touchMoveY(int y1,  int y2, int gap){
		Vector<String> cl = new Vector<String>();
		int y=y1;
		int yGap = (y2-y1)/gap;
		for(int g=0;g<gap;g++){
			y=y1+g*yGap;
			//send y command
			cl.add("sendevent /dev/input/event3 3 54 "+ y +"\n");
			cl.add("sendevent /dev/input/event3 0 0 0 \n");
			
		}
		return cl;
	}
	
	
	public static Vector<String> getCommandBySol(solution s){

		Vector<String> ret = new Vector<String>();
		// step 1. get init
		int inix = _inix+s.initcursor.w*_ballgap;
		int iniy = _iniy+s.initcursor.h*_ballgap;
		ret.addAll(touchDown(inix,iniy));
		
		int nowx = inix;
		int nowy = iniy;
		// step 2. add path
		for(Integer p:s.path){
			touchpos pos = changePathToPos(p);
			int passx = nowx;
			int passy = nowy;
			nowx += pos.x;
			nowy += pos.y;
			ret.addAll(touchMove(passx, passy, nowx, nowy, 1));
 		}
		ret.addAll(touchMove(nowx, nowy, nowx+20, nowy+20, 1));
		ret.addAll(touchUp());
		
		return ret;
	}
	public static Vector<String> getCommandByPath(int inith,int initw,String[] pathsetp){

		Vector<String> ret = new Vector<String>();
		// step 1. get init
		int inix = _inix+initw*_ballgap;
		int iniy = _iniy+inith*_ballgap;
		ret.addAll(touchDown(inix,iniy));
		
		int nowx = inix;
		int nowy = iniy;
		// step 2. add path
		for(String p:pathsetp){
			int pp = Integer.parseInt(p);
			touchpos pos = changePathToPos(pp);
			int passx = nowx;
			int passy = nowy;
			nowx += pos.x;
			nowy += pos.y;
			ret.addAll(touchMove(passx, passy, nowx, nowy, 1));
 		}
		ret.addAll(touchMove(nowx, nowy, nowx+20, nowy+20, 1));
		ret.addAll(touchUp());
		
		return ret;
	}
	
	public static touchpos changePathToPos(Integer p){
		/**
		 *  5 6 7
		 *  4 + 0
		 *  3 2 1
		 */
		touchpos ret =null;
		switch(p.intValue()){
		case 0:
			ret = new touchpos(_ballgap,0);
			break;
		case 1:
			ret = new touchpos(_ballgap,_ballgap);
			break;
		case 2:
			ret = new touchpos(0,_ballgap);
			break;
		case 3:
			ret = new touchpos(-_ballgap,_ballgap);
			break;
		case 4:
			ret = new touchpos(-_ballgap,0);
			break;
		case 5:
			ret = new touchpos(-_ballgap,_ballgap);
			break;
		case 6:
			ret = new touchpos(0,-_ballgap);
			break;
		case 7:
			ret = new touchpos(_ballgap,-_ballgap);
			break;
		}
		return ret;
	}

}
