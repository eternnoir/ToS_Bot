package com.tos_bot.ui;

import android.content.Context;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by Sean.
 */
public class FloatingTextView extends TextView implements IFloating{
    private IFloating floating = new Floating();
    public FloatingTextView(Context context) {
        super(context);
    }
    @Override
    public WindowManager.LayoutParams getLayoutParams(int x, int y) {
        return floating.getLayoutParams(x, y);
    }
}
