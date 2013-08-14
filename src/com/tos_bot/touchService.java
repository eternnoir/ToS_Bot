package com.tos_bot;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

import android.os.SystemClock;
import android.view.MotionEvent;

public class touchService {

	public static void myClickEvent(float x, float y) {
		Process sh;
		Vector<String> cl = new Vector<String>();
		cl.addAll(touchDown(100, 1700));
		cl.addAll(touchDown(100, 1700));
		cl.addAll(touchMove(100, 1700, 900, 1700, 3));
		cl.addAll(touchMove(900, 1700, 900, 2300, 3));
		cl.addAll(touchMove(900, 2300, 200, 2300, 3));
		cl.addAll(touchMove(200, 1600, 200, 1900, 3));
		cl.addAll(touchUp());
		try {
			sh = Runtime.getRuntime().exec("su", null, null);
			OutputStream os = sh.getOutputStream();
			for(String s:cl){
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
		cl.add("sendevent /dev/input/event3 3 57 713\n");
		cl.add("sendevent /dev/input/event3 3 48 83\n");
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

}
