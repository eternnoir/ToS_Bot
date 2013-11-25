package com.tos_bot.touchservice;

import com.tos_bot.ConfigData;
import com.tos_bot.touchservice.devices.AtecDevice;
import com.tos_bot.touchservice.devices.Gen_nexus_one;
import com.tos_bot.touchservice.devices.htc_new_one_m7;
import com.tos_bot.touchservice.devices.htc_one_x;
import com.tos_bot.touchservice.devices.Sony_Xperia_Z;

import net.atec.analyzer.Analizer;
import net.atec.sender.DeviceEvent;

public class touchDeviceFactory {
	public static String[] getDeviceList() {
		return new String[] { "Auto", "htc_new_one_m7", "htc_one_x",
				"Gen_nexus_one", "Sony_Xperia_Z" };
	}

	public static AbstractTouchService getNewTouchService(String deviceName) {
		AbstractTouchService ret = null;
		if (deviceName.equals("htc_new_one_m7")) {
			ret = new htc_new_one_m7();
			ret.setUp(270, 135, (int) ((2880 * 0.425) + 135));
		} else if (deviceName.equals("htc_one_x")) {
			ret = new htc_one_x();
			ret.setUp(180, 90, 890);
		}else if (deviceName.equals("Sony_Xperia_Z")){
			//magicat
			ret = new Sony_Xperia_Z();
			ret.setUp(180, 90, 710);
			
			
			
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
//magicat
		//		if (Build.MODEL.contains("C6602") || Build.MODEL.contains("C6603")) {
			//		ret.setUp(oneball, oneball / 2, (int) ((heigh - 120) * 0.45));

				//} else {
					ret.setUp(oneball, oneball / 2, (int) (heigh * 0.45) + (oneball / 2));
				//}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		return ret;
	}
}
