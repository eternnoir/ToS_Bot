package com.tos_bot.ui;

import android.content.Context;
import android.content.Intent;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.tos_bot.ConfigData;
import com.tos_bot.Constants;
import com.tos_bot.R;
import com.tos_bot.botService;
import com.tos_bot.weightMap;

import java.util.LinkedHashMap;

/**
 * Created by Sean.
 */
public class FloatingUIManager {
    final private int STANDARD_X = 720;
    final private int STANDARD_Y = 1280;
    private Display _display;
    private WindowManager _wm;
    private Context _context;
    private Observer _observer;
    private double imageButtonRatio = 1;

    private FloatingImageButton _floatStartButton = null;
    private FloatingImageButton _floatStopButton = null;
    private FloatingImageButton _floatStrategyButton = null;
    private FloatingLinearLayout _floatStrategyLayout = null;
    //private View _view;

    public FloatingUIManager(Context context, Display display, Observer observer){
        _display = display;
        imageButtonRatio = CalcRatio();
        _context = context;
        _wm = (WindowManager) context.getSystemService("window");
        _observer = observer;
        CreateAllButtons();
    }

    private void CreateAllButtons(){
        CreateStartButton();
        CreateStopButton();
        CreateStrategyButton();
        CreateStrategyHorizontalScrollView();
    }

    private void CreateStartButton(){
        _floatStartButton = CreateButton(_display.getWidth() / 8, _display.getHeight() / 8, "start");
        _floatStartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                _floatStartButton.setVisibility(View.INVISIBLE);
                _floatStrategyButton.setVisibility(View.INVISIBLE);
                _floatStopButton.setVisibility(View.VISIBLE);
                _observer.NotifyStart();
            }
        });
        _floatStartButton.setVisibility(View.INVISIBLE);
    }

    private void CreateStopButton(){
        _floatStopButton = CreateButton(_display.getWidth() / 2 + _display.getWidth() / 8,
                _display.getHeight() / 8, "stop");
        _floatStopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                _floatStopButton.setVisibility(View.INVISIBLE);
                _floatStartButton.setVisibility(View.VISIBLE);
                _floatStrategyButton.setVisibility(View.VISIBLE);
                if (ConfigData.solverThread != null) {
                    Thread moribund = ConfigData.solverThread;
                    ConfigData.solverThread = null;
                    moribund.interrupt();
                }
                _observer.NotifyStop();
            }
        });
        _floatStopButton.setVisibility(View.INVISIBLE);
    }

    private void CreateStrategyButton(){
        _floatStrategyButton = CreateButton(_display.getWidth() / 8, _display.getHeight() * 2 / 8,
                Constants.IdStringMap.get(ConfigData.StyleName) + "_Button");
        _floatStrategyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                _floatStrategyLayout.setVisibility(View.VISIBLE);
                _floatStrategyButton.setVisibility(View.INVISIBLE);
                _floatStartButton.setVisibility(View.INVISIBLE);
            }
        });
        _floatStrategyButton.setVisibility(View.INVISIBLE);
    }

    private void CreateStrategyHorizontalScrollView(){
        _floatStrategyLayout = new FloatingLinearLayout(_context);
        WindowManager.LayoutParams wmParams = _floatStrategyLayout.getLayoutParams(0, 0);

        HorizontalScrollView scrollView = new HorizontalScrollView(_context);
        scrollView.addView(getStrategyLinearLayout());
        _floatStrategyLayout.addView(scrollView);
        _wm.addView(_floatStrategyLayout, wmParams);
        _floatStrategyLayout.setVisibility(View.INVISIBLE);
    }

    private LinearLayout getStrategyLinearLayout() {
        LinearLayout layout = new LinearLayout(_context);
        Integer[] styleList = weightMap.getInstance().getStyleList();

        for (int i = 0; i < styleList.length; i++) {
            layout.addView(getImageButton(styleList[i]));
        }

        return layout;
    }

    private ImageButton getImageButton(Integer styleName) {
        StrategyImageButton button = new StrategyImageButton(_context);
        button.setId(styleName);
        button.setUpImage(styleName, imageButtonRatio);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ConfigData.StyleName = view.getId();
                _floatStrategyButton.setUpImage(
                        Constants.IdStringMap.get(ConfigData.StyleName) + "_Button",
                        imageButtonRatio);
                _floatStrategyLayout.setVisibility(View.INVISIBLE);
                _floatStartButton.setVisibility(View.VISIBLE);
                _floatStrategyButton.setVisibility(View.VISIBLE);
            }
        });
        return button;
    }

    private double CalcRatio(){
        double ratioX = _display.getWidth() / STANDARD_X;
        double ratioY = _display.getHeight() / STANDARD_Y;
        if (ratioX > ratioY)
            return ratioY;
        else
            return ratioX;
    }

    public void StartFloatingUI(){
        _floatStartButton.setVisibility(View.VISIBLE);
        _floatStrategyButton.setVisibility(View.VISIBLE);
        _floatStrategyLayout.setVisibility(View.INVISIBLE);
        _floatStopButton.setVisibility(View.INVISIBLE);
    }

    public void StopFloatingUI(){
        _floatStartButton.setVisibility(View.INVISIBLE);
        _floatStrategyButton.setVisibility(View.INVISIBLE);
        _floatStrategyLayout.setVisibility(View.INVISIBLE);
        _floatStopButton.setVisibility(View.INVISIBLE);
    }

    private FloatingImageButton CreateButton(int x, int y, String filename){
        FloatingImageButton button = new FloatingImageButton(_context);
        button.setUpImage(filename, imageButtonRatio);
        WindowManager.LayoutParams wmParams = button.getLayoutParams(x, y);
        _wm.addView(button, wmParams);
        return button;
    }
}
