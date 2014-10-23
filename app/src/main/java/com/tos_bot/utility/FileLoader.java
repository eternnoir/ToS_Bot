package com.tos_bot.utility;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;

public class FileLoader {
	private static Context ctx = null;
	private FileLoader(){
		
	}
	static public void setContext(Context c){
		ctx = c;
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
