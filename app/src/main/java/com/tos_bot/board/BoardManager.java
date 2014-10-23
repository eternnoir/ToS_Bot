package com.tos_bot.board;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.tos_bot.ConfigData;
import com.tos_bot.NotInTosException;



import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

public class BoardManager {
	static String _Path = "";

	private BoardManager() {

	}

	public static int[][] getBallArray() throws NotInTosException {
		IBoardProcesser bp = new TosBoardProcesser();
		return bp.getBoardOrbArrary();
	}
	public static void setOrbHash(){
		TosBoardProcesser bp = new TosBoardProcesser();
		ConfigData.baseOrbHash = bp.getBaseOrbFingerPrintMap();
	}

}
