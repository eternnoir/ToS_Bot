package com.tos_bot;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

public class imageProcesser {
	static String _Path = "";

	private imageProcesser() {

	}

	public static int ballsize;

	public static Bitmap cutBallReg(String FilePath) {
		_Path = FilePath;
		Bitmap sourceBitmap;
		sourceBitmap = BitmapFactory.decodeFile(FilePath);
		String deivceModel = Build.MODEL;
		// poor solution 
		if (deivceModel.contains("C6602") || deivceModel.contains("C6603") || // deivceModel.contains("LT26")||
				deivceModel.contains("LT29") || deivceModel.contains("C6802")) {
			sourceBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0,
					sourceBitmap.getWidth(),
					(int) (sourceBitmap.getWidth() / 0.6));
			Log.i("Bot:", "Sony Device");
		}
		int screenhigh = sourceBitmap.getHeight();
		int oneball = sourceBitmap.getWidth() / 6;
		ballsize = oneball;
		int ballAreaHigh = oneball * 5;
		float wh = (float) sourceBitmap.getWidth()
				/ (float) sourceBitmap.getHeight();
		Bitmap cropped = null;
		if (wh == 0.5625f || wh == 0.6f) { // 1080*1920 && 480*800
			cropped = Bitmap.createBitmap(sourceBitmap, 0,
					(int) (screenhigh * 0.45), sourceBitmap.getWidth(),
					ballAreaHigh);
		}
		// savePng("tmp_ballA", cropped);
		return cropped;

	}

	public static int[][] getBallArray(Bitmap c) throws NotInTosException {
		int[][] ret = new int[5][6];
		Bitmap sourceBitmap = c;
		for (int h = 0; h < 5; h++)
			for (int w = 0; w < 6; w++) {
				Bitmap cropped = Bitmap.createBitmap(sourceBitmap, w * ballsize
						+ ballsize - ballsize / 4, h * ballsize + ballsize / 2,
						ballsize / 16, ballsize / 16);
				// savePng("tmp" + h + w, cropped);
				ret[h][w] = checkBallColor(cropped) + 1;
			}
		int error = 0;
		for (int h = 0; h < 5; h++)
			for (int w = 0; w < 6; w++) {
				if (ret[h][w] == 0) {
					error++;
				}
			}
		if (error > 1) {
			throw new NotInTosException();
		}
		return ret;
	}

	public static int checkColor(Bitmap bm) {
		int ret = 0;

		getRGB(bm);

		return ret;
	}

	public static int[] getRGB(Bitmap bm) {
		int[] rgb = new int[3];
		int picw = bm.getWidth();
		int pich = bm.getHeight();
		int[] pix = new int[picw * pich];
		bm.getPixels(pix, 0, picw, 0, 0, picw, pich);
		int r, g, b;
		for (int i = 0; i < pix.length; i++) {
			r = (pix[i]) >> 16 & 0xff;
			g = (pix[i]) >> 8 & 0xff;
			b = (pix[i]) & 0xff;
			int dd = 0;
			dd = dd;
		}

		return rgb;
	}

	public static void savePng(String Filename, Bitmap bm) {
		try {
			File file = new File(_Path + Filename + ".png");
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
		int color[] = new int[6]; // RGBDLH
		int picw = bm.getWidth();
		int pich = bm.getHeight();
		int[] pix = new int[picw * pich];
		bm.getPixels(pix, 0, picw, 0, 0, picw, pich);
		int r, g, b;
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
			} else if (r < 100 && g < 150 && b > 200) {
				color[0]++; // blue
			} else if (r > 200 && g > 100 && b > 150 && g < 250) {
				color[5]++; // Hert
			} else if (r > 150 && g > 150 && b < 100) {
				color[3]++; // lght
			} else {
				// throw new NotInTosException();
			}

		}
		int ret = -1;
		int tmp = 0;
		for (int i = 0; i < color.length; i++) {
			if (color[i] > tmp) {
				tmp = color[i];
				ret = i;
			}
		}
		return ret;
	}
}
