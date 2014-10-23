package com.tos_bot.ui;

import android.content.Context;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by Sean.
 */
public class FloatingLinearLayout extends LinearLayout implements IFloating{
    private IFloating floating = new Floating();
    public FloatingLinearLayout(Context context){
        super(context);
    }

    @Override
    public WindowManager.LayoutParams getLayoutParams(int x, int y) {
        return floating.getLayoutParams(x, y);
    }
}
