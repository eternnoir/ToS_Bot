package net.atec.analyzer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.atec.sender.DeviceEvent;

public class ServiceCenter {
	/**
	 * 
	 * @param filePath
	 */
	public static void copyEventType(String filePath){
		Process p;
		try {
			// Preform su to get root privledges
			p = Runtime.getRuntime().exec("su", null, null);
			DataOutputStream os = new DataOutputStream(p.getOutputStream());
			String mkdircmd = "mkdir /mnt/sdcard/tmp\n";
			os.writeBytes(mkdircmd);
			// Attempt to write a file to a root-only
			String cmd ;
			cmd = "rm "+filePath+"/et1\n";
			os.write(cmd.getBytes());
			os.flush();
			cmd = "rm "+filePath+"/et2\n";
			os.write(cmd.getBytes());
			os.flush();
			cmd = "getevent -lp >> "+filePath+"/et1\n";
			os.write(cmd.getBytes());
			os.flush();
			cmd = "getevent -p >> "+filePath+"/et2\n";
			os.write(cmd.getBytes());
			os.flush();
			cmd = "chmod 777 "+filePath+"/et1\n";
			os.write(cmd.getBytes());
			os.flush();
			cmd = "chmod 777 "+filePath+"/et2\n";
			os.write(cmd.getBytes());
			os.flush();
			os.writeBytes("exit\n");
			os.flush();
			os.close();
			p.waitFor();
		} catch (Exception e) {
			// TODO Code to run in input/output exception
			// return false;
			e.printStackTrace();
		}
	}
	/**
	 * copy event format to tmp file
	 *	/sdcard/tmp/
	 */
	public static void copyEventType(){
		copyEventType("/sdcard/tmp");
	}
	
	public static String getEventFormat(String filePath){
		String ret = null;
		String fileStream = null;
		String fileStream2 = null;
		try {
			fileStream = getStringFromFile(filePath+"/et1");
			fileStream2 = getStringFromFile(filePath+"/et2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EventParser ep = new EventParser(fileStream,fileStream2);
		ep.findTouchscreenEvent();
		ep.findOtherAttr();
		
		return ret;
	}
	
	public static DeviceEvent getDeviceEvent(String filePath){
		DeviceEvent ret = null;
		String fileStream = null;
		String fileStream2 = null;
		try {
			fileStream = getStringFromFile(filePath+"/et1");
			fileStream2 = getStringFromFile(filePath+"/et2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EventParser ep = new EventParser(fileStream,fileStream2);
		ep.findTouchscreenEvent();
		ep.findOtherAttr();
		ret = ep.getNewDeviceEvent();
		
		
		return ret;
	}
	public static String convertStreamToString(InputStream is) throws Exception {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();
	    String line = null;
	    while ((line = reader.readLine()) != null) {
	      sb.append(line).append("\n");
	    }
	    return sb.toString();
	}

	public static String getStringFromFile (String filePath) throws Exception {
	    File fl = new File(filePath);
	    FileInputStream fin = new FileInputStream(fl);
	    String ret = convertStreamToString(fin);
	    //Make sure you close all streams.
	    fin.close();        
	    return ret;
	}
}
