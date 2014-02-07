package com.tos_bot;

import java.util.LinkedHashMap;

import android.content.SharedPreferences;


public class ConfigData {
	public static String Serverurl;
	public static int eightd = 0;   	// 0 is no eight direction
	public static int deep = 30;		// max move
	public static int waitForStageChageTimeSec = 12;
	public static String DeviceName = "";
	public static Integer StyleName = R.id.Vary_color_Single;
	public static String TempDir = "";
	public static Thread solverThread = null;
	public static String maxCombo = "0";		//0 is no limit
	public static String gap;			//the sleep time between two touch command
	public static int timeOut = 10;
	//For TouchEvent
	public static String touchEventNum;
	public static String posXId;
	public static String posYId;
	public static String posXMax;
	public static String posYMax;
	public static String trackingId;
	public static String pressureId;
	public static String trackingMax;
	public static String pressureMax;
	public static String oneBallMove;
	public static String startPosX;
	public static String startPosY;
	//For Image Setting
	public static int oneOrbWitdh=0;
	public static int boardStartX=0;
	public static int boardStartY=0;
	
	
	public static int MaxOrbType=6;
	public static LinkedHashMap<String, String> baseOrbHash = null;
	
	/**
	 * get config data from SharedPreferences
	 */
	static public void setConfig(SharedPreferences settings ){
		ConfigData.Serverurl = settings.getString("Serverurl", "http://tbserver.ap01.aws.af.cm/");
		ConfigData.deep = settings.getInt("deep", 30);
		ConfigData.maxCombo = settings.getString("maxCombo", "0");
		ConfigData.gap = settings.getString("gap","");
		ConfigData.DeviceName =  settings.getString("DeviceName","Auto");
		ConfigData.touchEventNum = settings.getString("touchEventNum","");
		ConfigData.posXId = settings.getString("posXId","");
		ConfigData.posYId = settings.getString("posYId","");
		ConfigData.posXMax = settings.getString("posXMax","");
		ConfigData.posYMax = settings.getString("posYMax","");
		ConfigData.trackingId = settings.getString("trackingId","");
		ConfigData.pressureId = settings.getString("pressureId","");
		ConfigData.trackingMax = settings.getString("trackingMax","");
		ConfigData.pressureMax = settings.getString("pressureMax","");
		ConfigData.oneBallMove = settings.getString("oneBallMove","");
		ConfigData.startPosX = settings.getString("startPosX","");
		ConfigData.startPosY = settings.getString("startPosY","");
		
		//For Image
		ConfigData.oneOrbWitdh = settings.getInt("oneOrbWitdh", 0);
		ConfigData.boardStartX = settings.getInt("boardStartX", 0);
		ConfigData.boardStartY = settings.getInt("boardStartY", 0);
		

	}
	
}
