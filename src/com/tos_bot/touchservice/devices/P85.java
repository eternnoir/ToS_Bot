package com.tos_bot.touchservice.devices;

import java.util.Vector;

import android.util.Log;

import com.tos_bot.touchservice.AbstractTouchService;


public class P85 extends AbstractTouchService {
	static int _lastY;
	static int _lastX;

	@Override
	public Vector<String> touchDown(int x, int y) {
		Vector<String> cl = new Vector<String>();
		cl.add("sendevent /dev/input/event1 3 57 0\n");			//0x39 ABS_MT_TRACKING_ID 
		cl.add("sendevent /dev/input/event1 3 48 200\n"); 		//0x30 ABS_MT_TOUCH_MAJOR 		
		cl.add("sendevent /dev/input/event1 3 53 " + x + "\n");	//0x35 ABS_MT_POSITION_X
		cl.add("sendevent /dev/input/event1 3 54 " + y + "\n");	//0x36 ABS_MT_POSITION_Y
		cl.add("sendevent /dev/input/event1 3 50 1\n");			//0x32 ABS_MT_WIDTH_MAJOR 
		cl.add("sendevent /dev/input/event1 0 2 0\n"); 
		cl.add("sendevent /dev/input/event1 0 0 0\n");
		_lastY = y;
		_lastX = x;
		Log.e("touchDown X",""+x);
		Log.e("touchDown Y",""+y);
		return cl;
	}

	@Override
	public Vector<String> touchUp() {
		Vector<String> cl = new Vector<String>();
		cl.add("sendevent /dev/input/event1 3 48 0\n");
		cl.add("sendevent /dev/input/event1 0 0 0 \n");
		return cl;
	}

	@Override
	public Vector<String> touchMove(int x1, int y1, int x2, int y2, int gap) {
		Vector<String> cl = new Vector<String>();


			cl.add("sendevent /dev/input/event1 3 57 0\n");			//0x39 ABS_MT_TRACKING_ID 
			cl.add("sendevent /dev/input/event1 3 48 200\n"); 		//0x30 ABS_MT_TOUCH_MAJOR 
			cl.add("sendevent /dev/input/event1 3 53 " + y2 + "\n");	//0x35 ABS_MT_POSITION_X 平板XY相反
			cl.add("sendevent /dev/input/event1 3 54 " + x2 + "\n");	//0x36 ABS_MT_POSITION_Y 平板XY相反
			cl.add("sendevent /dev/input/event1 3 50 1\n");			//0x32 ABS_MT_WIDTH_MAJOR 
			cl.add("sendevent /dev/input/event1 0 2 0\n"); 
			cl.add("sendevent /dev/input/event1 0 0 0\n");

		_lastY = y2;
		_lastX = x2;
		Log.e("touchMove X",""+x1);
		Log.e("touchMove Y",""+y1);
		return cl;
	}

	@Override
	public Vector<String> touchMoveY(int x1, int x2, int gap) {//左右上下相反
		Vector<String> cl = new Vector<String>();
		_lastX = x2-x1+_lastX;
		cl.add("sendevent /dev/input/event1 3 57 0\n");			//0x39 ABS_MT_TRACKING_ID 
		cl.add("sendevent /dev/input/event1 3 48 200\n"); 		//0x30 ABS_MT_TOUCH_MAJOR 
		cl.add("sendevent /dev/input/event1 3 53 " + _lastX + "\n");	//0x35 ABS_MT_POSITION_X 平板XY相反
		cl.add("sendevent /dev/input/event1 3 54 " + _lastY + "\n");	//0x36 ABS_MT_POSITION_Y 平板XY相反
		cl.add("sendevent /dev/input/event1 3 50 1\n");			//0x32 ABS_MT_WIDTH_MAJOR 
		cl.add("sendevent /dev/input/event1 0 2 0\n"); 
		cl.add("sendevent /dev/input/event1 0 0 0\n");
		
//		cl.add("sendevent /dev/input/event1 3 54 " + x2 + "\n"); 	//0x36 ABS_MT_POSITION_Y 平板XY相反
//		cl.add("sendevent /dev/input/event1 0 0 0 \n");
		
		
		Log.e("touchMoveY Y",""+_lastY);
		Log.e("touchMoveY X",""+_lastX);
		return cl;
	}

	
	@Override
	public Vector<String> touchMoveX(int y1, int y2, int gap) {//平板上 上下移動
		Vector<String> cl = new Vector<String>();
		_lastY = y2-y1+_lastY;
		cl.add("sendevent /dev/input/event1 3 57 0\n");			//0x39 ABS_MT_TRACKING_ID 
		cl.add("sendevent /dev/input/event1 3 48 200\n"); 		//0x30 ABS_MT_TOUCH_MAJOR 
		cl.add("sendevent /dev/input/event1 3 53 " + _lastX + "\n");	//0x35 ABS_MT_POSITION_X 平板XY相反
		cl.add("sendevent /dev/input/event1 3 54 " + _lastY + "\n");	//0x36 ABS_MT_POSITION_Y 平板XY相反
		cl.add("sendevent /dev/input/event1 3 50 1\n");			//0x32 ABS_MT_WIDTH_MAJOR 
		cl.add("sendevent /dev/input/event1 0 2 0\n"); 
		cl.add("sendevent /dev/input/event1 0 0 0\n");
		
//		cl.add("sendevent /dev/input/event1 3 54 " + x2 + "\n"); 	//0x36 ABS_MT_POSITION_Y 平板XY相反
//		cl.add("sendevent /dev/input/event1 0 0 0 \n");
		

		Log.e("touchMoveX X",""+_lastX);
		Log.e("touchMoveX Y",""+_lastY);
		return cl;
	}

}
