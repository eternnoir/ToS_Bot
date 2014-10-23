package com.tos_bot.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageButton;
import com.tos_bot.utility.FileLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by Sean.
 */
public abstract class TosImageButton extends ImageButton{
    public TosImageButton(Context context) {
        super(context);
    }

    protected Bitmap resizeImage(Bitmap srcImage, double ratio){
       return srcImage;
    	/*
    	return Bitmap.createScaledBitmap(
                srcImage,
                (int) (srcImage.getWidth() * ratio),
                (int) (srcImage.getHeight() * ratio),
                false);
                */
    }

    protected Bitmap getBitmapByFilename(String filename, double ratio) {
        FileLoader.setContext(this.getContext());
        BitmapFactory.Options option = new BitmapFactory.Options();

		option.inJustDecodeBounds = true;
        InputStream imageInputStream = FileLoader.getFileStreamByAsset("image/"
                + filename + ".png");
		BitmapFactory.decodeStream(imageInputStream, null, option);
		int width = (int) (option.outWidth*ratio);

		int height = (int) (option.outHeight*ratio);
        
        return decodeFile(imageInputStream,width,height);
    }
    
	public static Bitmap decodeFile(InputStream f, int WIDTH, int HIGHT) {
		try {
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(f, null, o);

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
			return BitmapFactory.decodeStream(f, null, o2);
		} catch (Exception e) {
		}
		return null;
	}

}
