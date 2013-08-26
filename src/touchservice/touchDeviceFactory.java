package touchservice;

import touchservice.devices.Gen_nexus_one;
import touchservice.devices.SonyXperiaTXLT29i;
import touchservice.devices.htc_new_one_m7;
import touchservice.devices.htc_one_x;

public class touchDeviceFactory {
	public static String[] getDeviceList() {
		return new String[] { "htc_new_one_m7", "htc_one_x", "Gen_nexus_one", "SonyXperiaTXLT29i" };
	}

	public static AbstractTouchService getNewTouchService(String deviceName) {
		AbstractTouchService ret = null;
		if (deviceName.equals("htc_new_one_m7")) {
			ret = new htc_new_one_m7();
			ret.setUp(270, 135, (int)((2880*0.425)+135));
		} else if (deviceName.equals("htc_one_x")) {
			ret = new htc_one_x();
			ret.setUp(180, 90, 890);
		} else if (deviceName.equals("Gen_nexus_one")) {
			ret = new Gen_nexus_one();
			ret.setUp(80, 40, 400);
		}
		else if (deviceName.equals("SonyXperiaTXLT29i")) {
			ret = new SonyXperiaTXLT29i();
			ret.setUp(120, 60, 604);
		}
		return ret;
	}
}
