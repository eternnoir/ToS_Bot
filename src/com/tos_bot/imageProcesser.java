package com.tos_bot;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Environment;
import android.util.Log;




public class imageProcesser {
	static String _Path = "";

	private imageProcesser() {
	}
	public static int ballsize;

	public static Bitmap cutBallReg(String FilePath) {
		_Path = FilePath;
		Bitmap sourceBitmap;
		String deivceModel = Build.MODEL;
		Log.i("Device:", deivceModel);
		//magicat
		if (deivceModel.contains("C6602")||deivceModel.contains("C6603")||//deivceModel.contains("LT26")||
				deivceModel.contains("LT29")||deivceModel.contains("C6802")) {
			sourceBitmap = BitmapFactory.decodeFile(FilePath);
			sourceBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0,
					sourceBitmap.getWidth(), (int)(sourceBitmap.getWidth() / 0.6));
			Log.i("Bot:", "Sony Device");
		}
		if (deivceModel.contains("P85")) {
			Log.i("Device:", deivceModel);
			Bitmap tempBitmap;
			tempBitmap = BitmapFactory.decodeFile(FilePath);
			//savePng("P85_tempBitmap", tempBitmap);
			Matrix m=new Matrix();
			//順時針旋轉90度
			m.postRotate(90); 
			//產生新的旋轉後Bitmap檔
			sourceBitmap = Bitmap.createBitmap(tempBitmap, 0, 0, tempBitmap.getWidth(), tempBitmap.getHeight(), m, true);
//			savePng("P85_sourceBitmap_Rotate", sourceBitmap);
//			
//			sourceBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0, sourceBitmap.getWidth(), (int)(sourceBitmap.getWidth() / 0.6));
//			savePng("P85_sourceBitmap_Cut", sourceBitmap);
		}
		else{
			sourceBitmap = BitmapFactory.decodeFile(FilePath);
		}

		int screenhigh = sourceBitmap.getHeight();
		float wh = (float) sourceBitmap.getWidth() / (float) sourceBitmap.getHeight();
		Bitmap cropped = null;
		if (sourceBitmap.getWidth() == 1080 || sourceBitmap.getWidth() == 720 || sourceBitmap.getWidth() == 1280) {
			//1080*1920 && 720*1080 && 1280*1920
			int oneball = sourceBitmap.getWidth() / 6;
			ballsize = oneball;
			int ballAreaHigh = oneball * 5;
			cropped = Bitmap.createBitmap(sourceBitmap, 0, (int)(screenhigh * 0.45),
					sourceBitmap.getWidth(), ballAreaHigh);
		}else if (sourceBitmap.getWidth() == 768) {		//768*1024 Tablet P85
			int oneball = 108;
			ballsize = oneball;
			cropped = Bitmap.createBitmap(sourceBitmap, 60, 435, oneball * 6, oneball * 5);
		}
		savePng("ballA_cropped", cropped);

		//magicat  Environment.getExternalStorageDirectory().getAbsolutePath() + 
		//savePng_magicat("/ballA_cropped", cropped);
		//savePng_magicat("/ballA_source", sourceBitmap);
		return cropped;
	}

	public static void savePng_magicat(String Filename, Bitmap bm) {
		try {
			File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/=tos" + Filename + ".png");
			file.createNewFile();
			BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream(file));
			bm.compress(CompressFormat.PNG, 100, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int[][] getBallArray(Bitmap c) throws NotInTosException {
		int[][] ret = new int[5][6];
		Bitmap sourceBitmap = c;
		for (int h = 0; h < 5; h++)
			for (int w = 0; w < 6; w++) {
				Bitmap cropped = Bitmap.createBitmap(sourceBitmap, w * ballsize //120-30 90
						+ ballsize - ballsize / 4, h * ballsize + ballsize / 2,//160
						ballsize / 16, ballsize / 16);
				//magicat
				//savePng_magicat("/getBallArraytmp_" + h + w, cropped);

				ret[h][w] = checkBallColor(cropped);
				//Log.e("ret[h][w]", "ret["+h+"]["+w+"]:"+ ret[h][w]);
			}
		int error = 0;
		for (int h = 0; h < 5; h++){
			for (int w = 0; w < 6; w++) {
				if (ret[h][w] == 0)
					error++;
			}
			Log.e("Board:", ""+ret[h][0]+ret[h][1]+ret[h][2]+ret[h][3]+ret[h][4]+ret[h][5]);
		}
		//太多無法判別 視為不在TOS當中 但要注意問號珠數量
		if (error > 10) {
			throw new NotInTosException();
		}
		return ret;
	}



	public static void savePng(String Filename, Bitmap bm) {
		try {
			File file = new File(_Path + Filename+".png");
			file.createNewFile();
			BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream(file));
			bm.compress(CompressFormat.PNG, 100, out);

			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int checkBallColor(Bitmap bm) throws NotInTosException {
		int color[] = new int[7]; // ?RGBDLH
		int picw = bm.getWidth();
		int pich = bm.getHeight();
		int[] pix = new int[picw * pich];
		bm.getPixels(pix, 0, picw, 0, 0, picw, pich);
		int r, g, b;
		//算出7*7裡面顏色分布
		for (int i = 0; i < pix.length; i++) {
			r = (pix[i]) >> 16 & 0xff;
			g = (pix[i]) >> 8 & 0xff;
			b = (pix[i]) & 0xff;
			if (r > 200 && b > 200 && g < 80) {
				color[4]++; // dark
			} else if (r > 200 && g < 100 && b < 100) {
				color[1]++; // red
			} else if (r < 150 && g > 150 && b < 150) {
				color[2]++; // green
			} else if (r < 100 && b > 180) { //B>180 Water B>220 ex-Water
				color[3]++; // blue
			} else if (r > 200 && g > 80 && b > 150) {
				color[6]++; // Heart
			} else if (r > 150 && g > 100 && b < 50) {
				color[5]++; // light
			} else {
				color[0]++;  //unknown ball
				// throw new NotInTosException();
			}

		}
		//找加總最多的
		int ColorMaxPos = -1;
		int tmpMax = -1;
		for (int i = 0; i < color.length; i++) {
			if (color[i] > tmpMax) {
				tmpMax = color[i];
				ColorMaxPos = i;
			}
		}
		return ColorMaxPos;
	}

}
