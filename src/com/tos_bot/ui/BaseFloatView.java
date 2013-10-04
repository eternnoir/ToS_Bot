package com.tos_bot.ui;

import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class BaseFloatView {
	private Display _display;
	private WindowManager _wm;
	private View _view;
	
	
	/**
	 * This class can get view layout by x,y position.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private WindowManager.LayoutParams getLayoutParams(int x, int y) {
		WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
		wmParams.gravity = Gravity.TOP | Gravity.LEFT;
		wmParams.x = x;
		wmParams.y = y;
		wmParams.type = 2002;	//Alert window must be 2002.
		wmParams.format = 1;
		wmParams.flags = 40;
		wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		return wmParams;
	}
	
}
