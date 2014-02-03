package com.tos_bot.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.tos_bot.ConfigData;
import com.tos_bot.Constants;
import com.tos_bot.weightMap;

/**
 * Created by Sean.
 */
public class FloatingUIManager {
    private int WIDTH;
    private int HEIGHT;
    private int RADIUS;
    private WindowManager _wm;
    private Context _context;
    private Observer _observer;
    private double imageButtonRatio = 1;
    private boolean isSetting = false;

    private FloatingImageButton _floatStartButton;
    private FloatingImageButton _floatStopButton;
    private FloatingImageButton _floatStrategyButton;
    private FloatingImageButton _floatSettingButton;
    private FloatingImageButton _floatVariableButton;
    private FloatingImageButton _floatReturnButton;
    private FloatingLinearLayout _floatStrategyLayout;
    private FloatingSeekBar _floatStepSeekBar;
    private FloatingSeekBar _floatComboSeekBar;
    private FloatingTextView _floatStepTextView;
    private FloatingTextView _floatComboTextView;
    //private View _view;

    public FloatingUIManager(Context context, Display display, Observer observer){
        WIDTH = display.getWidth();
        HEIGHT = display.getHeight();
        RADIUS = HEIGHT / 5;
        imageButtonRatio = CalcRatio();
        _context = context;
        _wm = (WindowManager) context.getSystemService("window");
        _observer = observer;
        CreateFloatingUI();
    }

    private void CreateFloatingUI(){
        CreateStartButton();
        CreateStopButton();
        CreateStrategyButton();
        CreateStrategyHorizontalScrollView();
        CreateSettingButton();
        CreateVariableButton();
        CreateStepSeekBar();
        CreateComboSeekBar();
        CreateReturnButton();
    }

    private void CreateStartButton(){
        int side = getStartButtonPosition();
        _floatStartButton = CreateButton(side, side, "start");
        _floatStartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                _floatStartButton.setVisibility(View.INVISIBLE);
                _floatStrategyButton.setVisibility(View.INVISIBLE);
                _floatSettingButton.setVisibility(View.INVISIBLE);
                _floatVariableButton.setVisibility(View.INVISIBLE);
                _floatStopButton.setVisibility(View.VISIBLE);
                isSetting = false;
                _observer.NotifyStart();
            }
        });
    }

    private int getStartButtonPosition(){
        return (int)(RADIUS / Math.sqrt(2));
    }

    private void CreateStopButton(){
        _floatStopButton = CreateButton(
                (int)(WIDTH * Constants.LEFT_TOP_WIDGET_X),
                (int)(HEIGHT * Constants.LEFT_TOP_WIDGET_Y), "stop");
        _floatStopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                _floatStopButton.setVisibility(View.INVISIBLE);
                _floatSettingButton.setVisibility(View.VISIBLE);
                if (ConfigData.solverThread != null) {
                    Thread moribund = ConfigData.solverThread;
                    ConfigData.solverThread = null;
                    moribund.interrupt();
                }
                _observer.NotifyStop();
            }
        });
    }

    private void CreateStrategyButton(){
        _floatStrategyButton = CreateButton(
                RADIUS, 0,
                Constants.IdStringMap.get(ConfigData.StyleName) + "_Button");
        _floatStrategyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                _floatStrategyLayout.setVisibility(View.VISIBLE);
                _floatStrategyButton.setVisibility(View.INVISIBLE);
                _floatStartButton.setVisibility(View.INVISIBLE);
                _floatSettingButton.setVisibility(View.INVISIBLE);
                _floatVariableButton.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void CreateStrategyHorizontalScrollView(){
        _floatStrategyLayout = new FloatingLinearLayout(_context);
        WindowManager.LayoutParams wmParams = _floatStrategyLayout.getLayoutParams(
                (int)Constants.LEFT_TOP_WIDGET_X,
                (int)Constants.LEFT_TOP_WIDGET_Y);

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
                _floatSettingButton.setVisibility(View.VISIBLE);
                _floatVariableButton.setVisibility(View.VISIBLE);
            }
        });
        return button;
    }

    private void CreateSettingButton(){
        _floatSettingButton = CreateButton(
                (int)Constants.LEFT_TOP_WIDGET_X,
                (int)Constants.LEFT_TOP_WIDGET_Y, "setting");
        _floatSettingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!isSetting) {
                    _floatVariableButton.setVisibility(View.VISIBLE);
                    _floatStrategyButton.setVisibility(View.VISIBLE);
                    _floatStartButton.setVisibility(View.VISIBLE);
                } else {
                    _floatVariableButton.setVisibility(View.INVISIBLE);
                    _floatStrategyButton.setVisibility(View.INVISIBLE);
                    _floatStartButton.setVisibility(View.INVISIBLE);
                }
                isSetting = !isSetting;
            }
        });
    }

    private void CreateVariableButton(){
        _floatVariableButton = CreateButton((int)Constants.LEFT_TOP_WIDGET_X, RADIUS, "variable");
        _floatVariableButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                _floatStartButton.setVisibility(View.INVISIBLE);
                _floatStrategyButton.setVisibility(View.INVISIBLE);
                _floatVariableButton.setVisibility(View.INVISIBLE);
                _floatSettingButton.setVisibility(View.INVISIBLE);
                _floatStepSeekBar.setVisibility(View.VISIBLE);
                _floatComboSeekBar.setVisibility(View.VISIBLE);
                _floatStepTextView.setVisibility(View.VISIBLE);
                _floatComboTextView.setVisibility(View.VISIBLE);
                _floatReturnButton.setVisibility(View.VISIBLE);
            }
        });
    }

    private void CreateStepSeekBar(){
        _floatStepSeekBar = CreateSeekBar(
                (int)(WIDTH * Constants.STEP_SEEK_BAR_X),
                (int)(HEIGHT * Constants.STEP_SEEK_BAR_Y));
        _floatStepSeekBar.setMax(Constants.STEP_SEEK_BAR_MAX);
        _floatStepSeekBar.setProgress(ConfigData.deep);
        _floatStepSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }
            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                _floatStepTextView.setText(String.valueOf(_floatStepSeekBar.getProgress()));
            }
        });
        _floatStepTextView = CreateTextView(
                (int)(WIDTH * Constants.SEEK_BAR_TEXT_X),
                (int)(HEIGHT * Constants.STEP_SEEK_BAR_Y));
        _floatStepTextView.setText(String.valueOf(_floatStepSeekBar.getProgress()));
    }

    private void CreateComboSeekBar(){
        _floatComboSeekBar = CreateSeekBar(
                (int)(WIDTH * Constants.COMBO_SEEK_BAR_X),
                (int)(HEIGHT * Constants.COMBO_SEEK_BAR_Y));
        _floatComboSeekBar.setMax(Constants.COMBO_SEEK_BAR_MAX);
        _floatComboSeekBar.setProgress(Integer.parseInt(ConfigData.maxCombo));
        _floatComboSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }
            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                _floatComboTextView.setText(String.valueOf(_floatComboSeekBar.getProgress()));
            }
        });
        _floatComboTextView = CreateTextView(
                (int)(WIDTH * Constants.SEEK_BAR_TEXT_X),
                (int)(HEIGHT * Constants.COMBO_SEEK_BAR_Y));
        _floatComboTextView.setText(String.valueOf(_floatComboSeekBar.getProgress()));
    }

    private void CreateReturnButton(){
        _floatReturnButton = CreateButton(
                (int)Constants.LEFT_TOP_WIDGET_X,
                (int)Constants.LEFT_TOP_WIDGET_Y, "return");
        _floatReturnButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ConfigData.deep = _floatStepSeekBar.getProgress();
                ConfigData.maxCombo = String.valueOf(_floatComboSeekBar.getProgress());
                _floatStartButton.setVisibility(View.VISIBLE);
                _floatStrategyButton.setVisibility(View.VISIBLE);
                _floatVariableButton.setVisibility(View.VISIBLE);
                _floatSettingButton.setVisibility(View.VISIBLE);
                _floatStepSeekBar.setVisibility(View.INVISIBLE);
                _floatComboSeekBar.setVisibility(View.INVISIBLE);
                _floatStepTextView.setVisibility(View.INVISIBLE);
                _floatComboTextView.setVisibility(View.INVISIBLE);
                _floatReturnButton.setVisibility(View.INVISIBLE);
            }
        });
    }

    private double CalcRatio(){
        double ratioX = WIDTH / Constants.STANDARD_X;
        double ratioY = HEIGHT / Constants.STANDARD_Y;
        if (ratioX > ratioY)
            return ratioY;
        else
            return ratioX;
    }

    public void StartFloatingUI(){
        _floatSettingButton.setVisibility(View.VISIBLE);
    }

    public void StopFloatingUI(){
        _floatStartButton.setVisibility(View.INVISIBLE);
        _floatStrategyButton.setVisibility(View.INVISIBLE);
        _floatStrategyLayout.setVisibility(View.INVISIBLE);
        _floatStopButton.setVisibility(View.INVISIBLE);
        _floatSettingButton.setVisibility(View.INVISIBLE);
        _floatVariableButton.setVisibility(View.INVISIBLE);
        _floatStepSeekBar.setVisibility(View.INVISIBLE);
        _floatComboSeekBar.setVisibility(View.INVISIBLE);
        _floatStepTextView.setVisibility(View.INVISIBLE);
        _floatComboTextView.setVisibility(View.INVISIBLE);
        _floatReturnButton.setVisibility(View.INVISIBLE);
    }

    private FloatingSeekBar CreateSeekBar(int x, int y){
        FloatingSeekBar seekBar = new FloatingSeekBar(_context);
        WindowManager.LayoutParams wmParams = seekBar.getLayoutParams(x, y);
        wmParams.width = (int)(WIDTH * Constants.SEEK_BAR_WIDTH);
        _wm.addView(seekBar, wmParams);
        seekBar.setVisibility(View.INVISIBLE);
        return seekBar;
    }

    private FloatingTextView CreateTextView(int x, int y){
        FloatingTextView textView = new FloatingTextView(_context);
        textView.setTextSize(Constants.SEEK_BAR_TEXT_SIZE);
        textView.setTypeface(null, Typeface.BOLD);
        WindowManager.LayoutParams wmParams = textView.getLayoutParams(x, y);
        _wm.addView(textView, wmParams);
        textView.setVisibility(View.INVISIBLE);
        return textView;
    }

    private FloatingImageButton CreateButton(int x, int y, String filename){
        FloatingImageButton button = new FloatingImageButton(_context);
        button.setUpImage(filename, imageButtonRatio);
        WindowManager.LayoutParams wmParams = button.getLayoutParams(x, y);
        _wm.addView(button, wmParams);
        button.setVisibility(View.INVISIBLE);
        return button;
    }
}
