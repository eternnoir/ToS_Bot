package com.tos_bot.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageButton;
import com.tos_bot.utility.FileLoader;
import java.io.InputStream;

/**
 * Created by Sean.
 */
public abstract class TosImageButton extends ImageButton{
    public TosImageButton(Context context) {
        super(context);
    }

    protected Bitmap resizeImage(Bitmap srcImage, double ratio){
        return Bitmap.createScaledBitmap(
                srcImage,
                (int) (srcImage.getWidth() * ratio),
                (int) (srcImage.getHeight() * ratio),
                false);
    }

    protected Bitmap getBitmapByFilename(String filename) {
        FileLoader.setContext(this.getContext());
        InputStream imageInputStream = FileLoader.getFileStreamByAsset("image/"
                + filename + ".png");
        return BitmapFactory.decodeStream(imageInputStream);
    }
}
