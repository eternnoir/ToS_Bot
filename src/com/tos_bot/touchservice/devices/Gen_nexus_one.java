package com.tos_bot.touchservice.devices;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Vector;

import net.pocketmagic.android.eventinjector.Events;
import net.pocketmagic.android.eventinjector.Events.InputDevice;

import android.os.SystemClock;

import com.tos_bot.touchservice.AbstractTouchService;


public class Gen_nexus_one extends AbstractTouchService {
	Events _ev;
	InputDevice _id;
	public Gen_nexus_one(){
		_ev = new Events();
		_ev.Init();

		chmod_event();
		ArrayList<InputDevice> x = _ev.m_Devs;
		for (InputDevice i : x) {
			if (i.getPath().equals("/dev/input/event7")) {
				_id = i;
			}
		}
		_id.Open(true);
		_id.setUp(0, 1, 0,16);
	}
	@Override
	public Vector<String> touchDown(int x, int y) {
		Vector<String> cl = new Vector<String>();
		cl.addAll(touchMove(x, y, x, y, 1));
		_id.SendEvent(1, 330, 1);
		_id.SendEvent(3, 24, 1);
		_id.SendEvent(0, 0, 0);
		return cl;
	}

	@Override
	public Vector<String> touchUp() {
		Vector<String> cl = new Vector<String>();
		_id.SendEvent(1, 330, 0);
		_id.SendEvent(3, 24, 0);
		_id.SendEvent(0, 0, 0);
		return cl;
	}

	@Override
	public Vector<String> touchMove(int x1, int y1, int x2, int y2, int gap) {
		Vector<String> cl = new Vector<String>();
		_id.SendEvent(3, 0, x2);
		_id.SendEvent(3, 1, y2);
		_id.SendEvent(0, 0, 0);
		int x=x1;
		int y=y1;
		int xGap = (x2-x1)/gap;
		int yGap = (y2-y1)/gap;
		for(int g=0;g<gap;g++){
			x=x1+g*xGap;
			y=y1+g*yGap;
			// send x command
			
		}
		SystemClock.sleep(gap);
		return cl;
	}

	@Override
	public Vector<String> touchMoveX(int x1, int x2, int gap) {
		Vector<String> cl = new Vector<String>();
		_id.SendEvent(3, 0, x2);
		_id.SendEvent(0, 0, 0);
		SystemClock.sleep(gap);
		return cl;
	}

	@Override
	public Vector<String> touchMoveY(int y1, int y2, int gap) {
		Vector<String> cl = new Vector<String>();
		_id.SendEvent(3, 1, y2);
		_id.SendEvent(0, 0, 0);
		SystemClock.sleep(gap);
		return cl;
	}
	public void chmod_event() {
		Process sh;
		try {
			sh = Runtime.getRuntime().exec("su", null, null);
			OutputStream os = sh.getOutputStream();
			os.write(("chmod 777 /dev/input/event7\n").getBytes("ASCII"));
			os.flush();
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
}
