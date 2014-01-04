package com.tos_bot;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

public class FileLoader {
	private static Context ctx = null;
    private static FileLoader ourInstance = new FileLoader();
	public static String[] SaveFileNameList = {"auto01","auto02","auto03","start","user01","user02","user03"};

	private FileLoader(){
	}
	static public void setContext(Context c){
		ctx = c;
	}
	
//    private final LinkedHashMap<Integer, String> SaveFileNameMap = new LinkedHashMap<Integer, String>() {
//        {
//			put(R.SaveFileName.auto01, "auto01");
//			put(R.SaveFileName.auto02, "auto02");
//			put(R.SaveFileName.auto03, "auto03");
//			put(R.SaveFileName.user01, "user01");
//			put(R.SaveFileName.user02, "user02");
//			put(R.SaveFileName.user03, "user03");
//        }
//    };
    public static FileLoader getInstance() {
        return ourInstance;
    }
//    public Integer[] getStyleList() {
//        return SaveFileNameMap.keySet().toArray(new Integer[0]);
//    }
    
	public static boolean LoadAndSave(String filename, boolean CMD_Type){ //LoadAndSave大絕招//False:load  True:save 
		String Saved_file_name = "/TOS_saved_"+filename+".xml";
		String _prefPath = "/data/data/com.madhead.tos.zh/shared_prefs/com.madhead.tos.zh.xml";
		String _prefPathMyCard = "/data/data/com.madhead.tos.zh.ex/shared_prefs/com.madhead.tos.zh.xml";
		String cmd;
		Process p;

		try {
			// Preform su to get root privledges
			p = Runtime.getRuntime().exec("su");
			DataOutputStream os = new DataOutputStream(p.getOutputStream());
			if(CMD_Type){ //False:load  True:save 
				if(filename=="auto"){ //auto round robin saving
					cmd = "cat " + ConfigData.TempDir+"/TOS_saved_"+SaveFileNameList[1]+".xml" + " > " + ConfigData.TempDir+"/TOS_saved_"+SaveFileNameList[2]+".xml" + "\n";
					Log.e("AUTO SAVE CMD:",cmd);
					os.writeBytes(cmd);

					cmd = "cat " + ConfigData.TempDir+"/TOS_saved_"+SaveFileNameList[0]+".xml" + " > " + ConfigData.TempDir+"/TOS_saved_"+SaveFileNameList[1]+".xml" + "\n";
					Log.e("AUTO SAVE CMD:",cmd);
					os.writeBytes(cmd);
										
					cmd = "cat " + _prefPath + " > " + ConfigData.TempDir+"/TOS_saved_"+SaveFileNameList[0]+".xml" + "\n";
					Log.e("AUTO SAVE CMD:",cmd);
					os.writeBytes(cmd);					
				}
				else if(filename=="start"){ //開始執行時的自動存檔
					cmd = "cat " + _prefPath + " > " + ConfigData.TempDir+"/TOS_saved_"+SaveFileNameList[3]+".xml" + "\n";
					Log.e("Start SAVE CMD:",cmd);
					os.writeBytes(cmd);
				}
				else{ //user saving
					cmd = "cat " + _prefPath + " > " + ConfigData.TempDir+Saved_file_name + "\n"; //save
					Log.e("USER SAVE CMD:",cmd);
					os.writeBytes(cmd);
				}
			}
			else{ //False:load file section
				if(filename=="clear"){ //清除全部存檔
					for(int i = 0; i < SaveFileNameList.length; i++){		
					cmd = "rm " + ConfigData.TempDir+"/TOS_saved_"+SaveFileNameList[i]+".xml" + "\n";
					Log.e("Clear CMD:",cmd);
					os.writeBytes(cmd);				
					}
				}
				else{//一般讀檔
					cmd = "cat " + ConfigData.TempDir+Saved_file_name + " > " + _prefPath + "\n"; //load
					Log.e("LOAD CMD:",cmd);
					os.writeBytes(cmd);
				}
			}
			//cmd = "cp " + _MyCardFp + " /sdcard/TOS_tmp.xml\n";
			//os.writeBytes(cmd);

			// Close the terminal
			os.writeBytes("exit\n");
			os.flush();

			try {
				p.waitFor();
				if (p.exitValue() != 255) {
					// TODO Code to run on success
					// return true;
				} else {
					// TODO Code to run on unsuccessful
					// return false;
				}
			} catch (InterruptedException e) {
				// TODO Code to run in interrupted exception
				// return false;
			}
		} catch (IOException e) {
			// TODO Code to run in input/output exception
			// return false;
		}
		return true;
	}
	

	
	static public InputStream getFileStreamByAsset(String path){
		assert(ctx!=null);
		AssetManager am = ctx.getAssets();
		try {
			return am.open(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
