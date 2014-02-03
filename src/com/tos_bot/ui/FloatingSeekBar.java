package com.tos_bot.ui;

import android.content.Context;
import android.view.WindowManager;
import android.widget.SeekBar;

/**
 * Created by Sean.
 */
public class FloatingSeekBar extends SeekBar implements IFloating{
    private IFloating floating = new Floating();
    public FloatingSeekBar(Context context) {
        super(context);
    }

    @Override
    public WindowManager.LayoutParams getLayoutParams(int x, int y) {
        return floating.getLayoutParams(x, y);
    }
}
