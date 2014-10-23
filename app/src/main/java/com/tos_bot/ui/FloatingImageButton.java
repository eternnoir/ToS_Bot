package com.tos_bot.ui;

import android.view.WindowManager;
import android.graphics.Bitmap;
import android.content.Context;

/**
 * Created by Sean.
 */
public class FloatingImageButton extends TosImageButton implements IFloating{
    private IFloating floating = new Floating();
    public FloatingImageButton(Context context){
        super(context);
        this.getBackground().setAlpha(0);
    }

    @Override
    public WindowManager.LayoutParams getLayoutParams(int x, int y) {
        return floating.getLayoutParams(x, y);
    }

    public void setUpImage(String filename, double ratio){
        Bitmap srcImage = getBitmapByFilename(filename,ratio);
        this.setImageBitmap(srcImage);
    }
}
