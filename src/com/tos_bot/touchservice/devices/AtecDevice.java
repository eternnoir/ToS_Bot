package com.tos_bot.touchservice.devices;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Vector;

import android.os.Handler;
import android.os.SystemClock;

import com.tos_bot.touchservice.AbstractTouchService;

import net.atec.sender.DeviceEvent;
import net.pocketmagic.android.eventinjector.Events;
import net.pocketmagic.android.eventinjector.Events.InputDevice;

public class AtecDevice extends AbstractTouchService {
	DeviceEvent _de;
	Events _ev;
	InputDevice _id;

	public AtecDevice(DeviceEvent de) {
		_de = de;
		_ev = new Events();
		_ev.Init();

		chmod_event();
		ArrayList<InputDevice> x = _ev.m_Devs;
		for (InputDevice i : x) {
			if (i.getPath().equals(_de.getEvent())) {
				_id = i;
			}
		}
		_id.Open(true);
		_id.setUp(_de.getXid(), _de.getYid(), _de.getTrackingIDid(),
				_de.getPurssureid());
	}

	@Override
	public Vector<String> touchDown(int x, int y) {
		Vector<String> cl = new Vector<String>();
		_id.SendTouchDownAbs(x, y);
		return cl;
	}

	@Override
	public Vector<String> touchUp() {
		Vector<String> cl = new Vector<String>();
		_id.SendTouchRelease();
		return cl;
	}

	@Override
	public Vector<String> touchMove(int x1, int y1, final int x2, final int y2,
			int gap) {
		Vector<String> cl = new Vector<String>();
		int x = x1;
		int y = y1;
		_id.SendTouchAbsCoord(x2, y2);
		SystemClock.sleep(gap);
		return cl;

	}

	@Override
	public Vector<String> touchMoveX(int x1, int x2, int gap) {
		Vector<String> cl = new Vector<String>();
		_id.SendTouchAbsXCoord(x2);
		SystemClock.sleep(gap);

		return cl;
	}

	@Override
	public Vector<String> touchMoveY(int y1, int y2, int gap) {
		Vector<String> cl = new Vector<String>();
		_id.SendTouchAbsYCoord(y2);
		SystemClock.sleep(gap);
		return cl;
	}

	public void chmod_event() {
		Process sh;
		try {
			sh = Runtime.getRuntime().exec("su", null, null);
			OutputStream os = sh.getOutputStream();
			os.write(("chmod 777 " + _de.getEvent() + "\n").getBytes("ASCII"));
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
