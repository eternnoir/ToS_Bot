package com.tos_bot;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;

public class imageProcesser {
	private imageProcesser() {

	}

	public static int ballsize;

	public static Bitmap cutBallReg(String FilePath) {

		Bitmap sourceBitmap;
		sourceBitmap = BitmapFactory.decodeFile("/sdcard/tmp/img.png");
		int screenhigh = sourceBitmap.getHeight();
		int oneball = sourceBitmap.getWidth() / 6;
		ballsize = oneball;
		int ballAreaHigh = oneball * 5;
		float wh = (float) sourceBitmap.getWidth()
				/ (float) sourceBitmap.getHeight();
		Bitmap cropped = null;
		if (wh == 0.5625f) {		//1080*1920
			cropped = Bitmap.createBitmap(sourceBitmap, 0, ballAreaHigh
					- (oneball / 6), sourceBitmap.getWidth(),ballAreaHigh);
		} else if (wh == 0.6f) {	//480*800
			cropped = Bitmap.createBitmap(sourceBitmap, 0,(int)(screenhigh*0.45),
					sourceBitmap.getWidth(),ballAreaHigh);
		}
		savePng("tmp_ballA", cropped);
		return cropped;

	}

	public static int[][] getBallArray(Bitmap c) throws NotInTosException {
		int[][] ret = new int[5][6];
		// BitmapFactory.decodeFile("/sdcard/tmp/img3.png");
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
		if (error > 5) {
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
			File file = new File("/sdcard/tmp/" + Filename);
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

	public static Bitmap highD(Bitmap bm) {
		int picw = bm.getWidth();
		int pich = bm.getHeight();
		int[] pix = new int[picw * pich];
		bm.getPixels(pix, 0, picw, 0, 0, picw, pich);
		int r, g, b;
		for (int i = 0; i < pix.length; i++) {
			r = (pix[i]) >> 16 & 0xff;
			g = (pix[i]) >> 8 & 0xff;
			b = (pix[i]) & 0xff;
		}
		return bm;

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
			} else if (r > 200 && g > 100 && b > 150 && g < 200) {
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
