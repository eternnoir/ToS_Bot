package touchservice;

import touchservice.devices.htc_new_one_m7;

public class touchDeviceFactory {
	public static String[] getDeviceList(){
		return new String[]{
				"htc_new_one_m7",
				"hte_one"
			};
		}
	
	public static ITouchService getNewTouchService(String deviceName){
		if(deviceName.equals("htc_new_one_m7")){
			return new htc_new_one_m7();
		}else 	if(deviceName.equals("htc_one")){
			return new htc_new_one_m7();
		}
		
		return null;
	}
}
