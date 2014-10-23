package com.tos_bot.utility;

import android.graphics.Bitmap;

public class SimilarImageSearch {
	public static String produceFingerPrint(Bitmap bm) {
		int owidth = 32;
		int oheight = 32;
		Bitmap sImg = ImageHelper.resize(bm, owidth, oheight);
		int width = 16;
		int height = 16;
		sImg = Bitmap.createBitmap(sImg, 8,8, width, height);
		
		int[] pixels = ImageHelper.BitmaptoGrayscale(sImg);
		sImg = null;
		
		int avgPixel = ImageHelper.average(pixels);
		
		int[] comps = new int[width * height];
		for (int i = 0; i < comps.length; i++) {
			if (pixels[i] >= avgPixel) {
				comps[i] = 1;
			} else {
				comps[i] = 0;
			}
		}
		
		StringBuffer hashCode = new StringBuffer();
		for (int i = 0; i < comps.length; i+= 4) {
			int result = comps[i] * (int) Math.pow(2, 3) + comps[i + 1] * (int) Math.pow(2, 2) + comps[i + 2] * (int) Math.pow(2, 1) + comps[i + 2];
			hashCode.append(ImageHelper.binaryToHex(result));
		}
		
		return hashCode.toString();
		
	}
	
	public static int hammingDistance(String sourceHashCode, String hashCode) {
		int difference = 0;
		int len = sourceHashCode.length();
		
		for (int i = 0; i < len; i++) {
			if (sourceHashCode.charAt(i) != hashCode.charAt(i)) {
				difference ++;
			} 
		}
		
		return difference;
	}
}
