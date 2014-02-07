package com.tos_bot.board;

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
	@Override
	public int[][] getBoardOrbArrary() throws NotInTosException {
		result = new int[5][6];
		for (int h = 0; h < 5; h++)
			for (int w = 0; w < 6; w++) {
				result[h][w]=-1;
			}
		String orbsHash[][] = bitmapToHash(getOrbBitmapArray(getCropedBitmap()));
		for (Iterator<String> iterator = ConfigData.baseOrbHash.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String baseHash = ConfigData.baseOrbHash.get(key);
            setBallType(baseHash,orbsHash,key.charAt(0)+"");
        }
		return result;
	}
	
	private void setBallType(String basehash,String[][] hash,String i){
		for (int h = 0; h < 5; h++){
			for (int w = 0; w < 6; w++) {
				if(result[h][w]<0){
					int hd = SimilarImageSearch.hammingDistance(basehash, hash[h][w]);
					if(hd<15){
						result[h][w] = Integer.parseInt(i);
					}
				}
			}
		}
	}
	
	private Bitmap getCropedBitmap(){
		Bitmap sourceBitmap;
		sourceBitmap = BitmapFactory.decodeFile(_Path);
		int ballsize = ConfigData.oneOrbWitdh;
		int ballAreaHigh = ballsize * 5;
		Bitmap cropped = null;
			cropped = Bitmap.createBitmap(sourceBitmap, ConfigData.boardStartX,
					ConfigData.boardStartY, ballsize*6,
					ballAreaHigh);
		// savePng("tmp_ballA", cropped);
		return cropped;
	}
	
	private Bitmap[][] getOrbBitmapArray(Bitmap sbm){
		Bitmap ret[][] = new Bitmap[5][6];
		int ballsize = ConfigData.oneOrbWitdh;

		for (int h = 0; h < 5; h++)
			for (int w = 0; w < 6; w++) {
				Bitmap cropped = Bitmap.createBitmap(sbm, w * ballsize
						, h * ballsize,
						ballsize, ballsize);
				// savePng("tmp" + h + w, cropped);
				ret[h][w] = cropped;
				ImageHelper.savePng(ConfigData.TempDir+h+w+".png",cropped);
			}
		
		return ret;
	}
	
	private String[][] bitmapToHash(Bitmap[][] sc){
		String[][] ret = new String[5][6];
		for (int h = 0; h < 5; h++)
			for (int w = 0; w < 6; w++) {
				ret[h][w] = SimilarImageSearch.produceFingerPrint(sc[h][w]);
			}
		return ret;
	}
	
	public Bitmap[] getBaseOrbBitmaps(){
		Bitmap[] ret = new Bitmap[ConfigData.MaxOrbType*2];
		for(int i=0; i<ConfigData.MaxOrbType;i++){
			InputStream orbNI = FileLoader.getFileStreamByAsset("OrbBase/" + (i+1) + "0.png");
			InputStream orbPI = FileLoader.getFileStreamByAsset("OrbBase/" + (i+1) + "1.png");
			Bitmap orbN= BitmapFactory.decodeStream(orbNI);
			Bitmap orbP= BitmapFactory.decodeStream(orbPI);
			ret[i*2] = orbN;
			ret[i*2+1] = orbP;
		}
		return ret;
	}
	
	public LinkedHashMap<String, String> getBaseOrbFingerPrintMap(){
		LinkedHashMap<String, String> ret = new LinkedHashMap<String, String>();
		Bitmap[] baseOrb = getBaseOrbBitmaps();
		for(int i=0; i<ConfigData.MaxOrbType;i++){
			String fpN = SimilarImageSearch.produceFingerPrint(baseOrb[i*2]);
			ret.put((i+1)+"0",fpN);
			String fpP = SimilarImageSearch.produceFingerPrint(baseOrb[i*2 +1]);
			ret.put((i+1)+"1",fpP);
		}
		return ret;
	}

}
