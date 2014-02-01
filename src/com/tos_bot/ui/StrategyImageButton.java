package com.tos_bot.ui;

import android.content.Context;
import android.graphics.Bitmap;
import com.tos_bot.Constants;

/**
 * Created by Sean.
 */
public class StrategyImageButton extends TosImageButton{

    public StrategyImageButton(Context context){
        super(context);
        this.getBackground().setAlpha(0);
    }

    public void setUpImage(Integer styleName, double ratio){
        Bitmap srcImage = getBitmapByFilename(Constants.IdStringMap.get(styleName));
        this.setImageBitmap(resizeImage(srcImage, ratio));
    }
}
