package touchservice;

import touchservice.devices.htc_new_one_m7;
import touchservice.devices.htc_one;

public class touchDeviceFactory {
	public static String[] getDeviceList(){
		return new String[]{
				"htc_new_one_m7",
				"hte_one"
			};
		}
	
	public static AbstractTouchService getNewTouchService(String deviceName){
		if(deviceName.equals("htc_new_one_m7")){
			AbstractTouchService ret = new htc_new_one_m7();
			ret.setUp(270, 135, 1380);
			return ret;
		}else 	if(deviceName.equals("htc_one")){
			AbstractTouchService ret = new htc_one();
			ret.setUp(180, 90, 890);
			return new htc_new_one_m7();
		}
		
		return null;
	}
}
