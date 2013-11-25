package net.atec.analyzer;


import net.atec.sender.DeviceEvent;

public class Analizer {
	
	private String _dir="";
	public Analizer(){
		
	}
	
	public String getDeviceEventFormat(){
		String ret = null;
			
		return ret;
	}
	
	public void setFileDir(String dir){
		_dir = dir;
	}
	
	public DeviceEvent getDeviceEvent(){
		
		ServiceCenter.copyEventType(_dir);
		DeviceEvent de = ServiceCenter.getDeviceEvent(_dir);
		return de;
	}
	
	
	
}
