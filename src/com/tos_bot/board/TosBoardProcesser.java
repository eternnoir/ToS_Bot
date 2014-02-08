package com.tos_bot.board;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.DropBoxManager.Entry;

import com.tos_bot.ConfigData;
import com.tos_bot.NotInTosException;
import com.tos_bot.utility.FileLoader;
import com.tos_bot.utility.ImageHelper;
import com.tos_bot.utility.SimilarImageSearch;

public class TosBoardProcesser implements IBoardProcesser {
	private String _Path = ConfigData.TempDir + "/img.png";
	private int[][] result;
	private int ballsize;
	private int startX ;
	private int startY;

	@Override
	public int[][] getBoardOrbArrary() throws NotInTosException {
		System.gc();
		result = new int[5][6];
		for (int h = 0; h < 5; h++)
			for (int w = 0; w < 6; w++) {
				result[h][w] = -1;
			}
		String orbsHash[][] = bitmapToHash(getOrbBitmapArray(getCropedBitmap()));
		for (Iterator<String> iterator = ConfigData.baseOrbHash.keySet()
				.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			String baseHash = ConfigData.baseOrbHash.get(key);
			setBallType(baseHash, orbsHash, key.charAt(0) + "");
		}
		for (int h = 0; h < 5; h++)
			for (int w = 0; w < 6; w++) {
				if (result[h][w] < 0) {
					result[h][w] = 0;
					//throw new NotInTosException();
				}
			}
		System.gc();
		return result;
	}

	private void setBallType(String basehash, String[][] hash, String i) {
		for (int h = 0; h < 5; h++) {
			for (int w = 0; w < 6; w++) {
				if (result[h][w] < 0) {
					int hd = SimilarImageSearch.hammingDistance(basehash,
							hash[h][w]);
					if (hd < 5) {
						result[h][w] = Integer.parseInt(i);
					}
				}
			}
		}
	}

	private Bitmap getCropedBitmap() {
		Bitmap sourceBitmap;
		BitmapFactory.Options option = new BitmapFactory.Options();

		option.inJustDecodeBounds = true;

		BitmapFactory.decodeFile(_Path, option);
		int width = option.outWidth;

		int height = option.outHeight;
		File f = new File(_Path);
		sourceBitmap = decodeFile(f, width, height );
		ballsize= ConfigData.oneOrbWitdh;
		startX =ConfigData.boardStartX;
		startY = ConfigData.boardStartY;
		int ballAreaHigh = ballsize * 5;
		Bitmap cropped = null;
		cropped = Bitmap.createBitmap(sourceBitmap, startX,
				startY, ballsize * 6, ballAreaHigh);
		// savePng("tmp_ballA", cropped);
		return cropped;
	}

	public static Bitmap decodeFile(File f, int WIDTH, int HIGHT) {
		try {
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// The new size we want to scale to
			final int REQUIRED_WIDTH = WIDTH;
			final int REQUIRED_HIGHT = HIGHT;
			// Find the correct scale value. It should be the power of 2.
			int scale = 1;
			while (o.outWidth / scale / 2 >= REQUIRED_WIDTH
					&& o.outHeight / scale / 2 >= REQUIRED_HIGHT)
				scale *= 2;

			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}

	private Bitmap[][] getOrbBitmapArray(Bitmap sbm) {
		Bitmap ret[][] = new Bitmap[5][6];

		for (int h = 0; h < 5; h++)
			for (int w = 0; w < 6; w++) {
				Bitmap cropped = Bitmap.createBitmap(sbm, w * ballsize, h
						* ballsize, ballsize, ballsize);
				// savePng("tmp" + h + w, cropped);
				ret[h][w] = cropped;
				// ImageHelper.savePng(ConfigData.TempDir+h+w+".png",cropped);
			}

		return ret;
	}

	private String[][] bitmapToHash(Bitmap[][] sc) {
		String[][] ret = new String[5][6];
		for (int h = 0; h < 5; h++)
			for (int w = 0; w < 6; w++) {
				ret[h][w] = SimilarImageSearch.produceFingerPrint(sc[h][w]);
			}
		return ret;
	}

	public Bitmap[] getBaseOrbBitmaps() {
		Bitmap[] ret = new Bitmap[ConfigData.MaxOrbType * 2];
		for (int i = 0; i < ConfigData.MaxOrbType; i++) {
			InputStream orbNI = FileLoader.getFileStreamByAsset("OrbBasePD/"
					+ (i + 1) + "0.png");
			InputStream orbPI = FileLoader.getFileStreamByAsset("OrbBasePD/"
					+ (i + 1) + "1.png");
			Bitmap orbN = BitmapFactory.decodeStream(orbNI);
			Bitmap orbP = BitmapFactory.decodeStream(orbPI);
			ret[i * 2] = orbN;
			ret[i * 2 + 1] = orbP;
		}
		return ret;
	}

	public LinkedHashMap<String, String> getBaseOrbFingerPrintMap() {
		LinkedHashMap<String, String> ret = new LinkedHashMap<String, String>();
		Bitmap[] baseOrb = getBaseOrbBitmaps();
		for (int i = 0; i < ConfigData.MaxOrbType; i++) {
			String fpN = SimilarImageSearch.produceFingerPrint(baseOrb[i * 2]);
			ret.put((i + 1) + "0", fpN);
			String fpP = SimilarImageSearch
					.produceFingerPrint(baseOrb[i * 2 + 1]);
			ret.put((i + 1) + "1", fpP);
		}
		return ret;
	}

}
