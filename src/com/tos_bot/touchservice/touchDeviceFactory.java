package com.tos_bot.touchservice;

import com.tos_bot.ConfigData;
import com.tos_bot.touchservice.devices.AtecDevice;
import com.tos_bot.touchservice.devices.Gen_nexus_one;
import com.tos_bot.touchservice.devices.htc_new_one_m7;
import com.tos_bot.touchservice.devices.htc_one_x;
import com.tos_bot.touchservice.devices.sony_xperia_Z;

import android.graphics.Bitmap;
import net.atec.analyzer.Analizer;
import net.atec.sender.DeviceEvent;

public class touchDeviceFactory {
	public static AbstractTouchService getNewTouchService(){
		AbstractTouchService ret = null;
		// DeviceEvent(String e,String px,String py,String Tid,String pre, String xM,String yM,String tiM,String pM)
		DeviceEvent de = new DeviceEvent(ConfigData.touchEventNum, 
				ConfigData.posXId, ConfigData.posYId, ConfigData.trackingId, ConfigData.pressureId,
				ConfigData.posXMax, ConfigData.posYMax, ConfigData.trackingMax, ConfigData.pressureMax);
		ret = new AtecDevice(de);
		ret.setUp(Integer.parseInt(ConfigData.oneBallMove), 
				Integer.parseInt(ConfigData.startPosX),
				Integer.parseInt(ConfigData.startPosY));
		return ret;
	}
/*
	public static AbstractTouchService getNewTouchService(String deviceName) {
		AbstractTouchService ret = null;
		if (deviceName.equals("htc_new_one_m7")) {
			ret = new htc_new_one_m7();
			ret.setUp(270, 135, (int) ((2880 * 0.425) + 135));
		} else if (deviceName.equals("htc_one_x")) {
			ret = new htc_one_x();
			ret.setUp(180, 90, 890);
		} else if (deviceName.equals("Sony_Xperia_Z")) {
			// magicat
			ret = new sony_xperia_Z();
			ret.setUp(180, 90, 858);

		} else if (deviceName.equals("Gen_nexus_one")) {
			ret = new Gen_nexus_one();
			ret.setUp(80, 40, 400);
		} else if (deviceName.equals("Auto")) {
			Analizer an = new Analizer();
			try {
				an.setFileDir(ConfigData.TempDir);
				DeviceEvent de;
				de = an.getDeviceEvent();
				int width = Integer.parseInt(de.getScreenXMax());
				int oneball = width / 6;
				int heigh = Integer.parseInt(de.getScreenYMax());
				ret = new AtecDevice(de);
				ret.setUp(oneball, oneball / 2, (int) (heigh * 0.45)
						+ (oneball / 2));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		return ret;
	}
	*/
}
