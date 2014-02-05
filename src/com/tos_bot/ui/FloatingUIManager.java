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
import com.tos_bot.ui.state.State;
import com.tos_bot.weightMap;
import com.tos_bot.ui.state.InitPage;
import com.tos_bot.ui.state.SettingPage;
import com.tos_bot.ui.state.StartServicePage;
import com.tos_bot.ui.state.VariablePage;
import com.tos_bot.ui.state.StrategyPage;

import java.util.ArrayList;
import java.util.List;

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
    private State current;
    private ArrayList<View> _views = new ArrayList<View>();
    private InitPage initPage;
    private SettingPage settingPage;
    private StartServicePage startServicePage;
    private VariablePage variablePage;
    private StrategyPage strategyPage;

    private FloatingImageButton _floatStrategyButton;
    private FloatingImageButton _floatSettingButton;
    //private View _view;

    public void Init(){
        current = initPage;
        current.Init();
    }

    public void Setting() {
        current = settingPage;
        current.Setting();
    }

    public void Variable() {
        current = variablePage;
        current.Variable();
    }

    public void Strategy() {
        current = strategyPage;
        current.Strategy();
    }

    public void StartService() {
        current = startServicePage;
        current.StartService();
    }

    public ArrayList<View> RegisterWidget(InitPage page){
        final FloatingImageButton button = CreateSettingButton();
        _floatSettingButton = button;
        _views.add(button);
        return new ArrayList<View>(){{
            add(button);
        }};
    }

    public ArrayList<View> RegisterWidget(SettingPage page){
        final FloatingImageButton button1 = CreateStartButton();
        final FloatingImageButton button2 = CreateStrategyButton();
        final FloatingImageButton button3 = CreateVariableButton();
        _floatStrategyButton = button2;
        _views.add(button1);
        _views.add(button2);
        _views.add(button3);
        return new ArrayList<View>(){{
            add(button1);
            add(button2);
            add(button3);
            add(_floatSettingButton);
        }};
    }

    public ArrayList<View> RegisterWidget(StartServicePage page){
        final FloatingImageButton button = CreateStopButton();
        _views.add(button);
        return new ArrayList<View>(){{
            add(button);
        }};
    }

    public ArrayList<View> RegisterWidget(VariablePage page){
        final FloatingSeekBar seekBar1;
        final FloatingSeekBar seekBar2;
        final FloatingTextView text1 = CreateStepTextView();
        final FloatingTextView text2 = CreateComboTextView();
        final FloatingImageButton button = CreateReturnButton();
        seekBar1 = CreateStepSeekBar(text1);
        seekBar2 = CreateComboSeekBar(text2);
        _views.add(seekBar1);
        _views.add(seekBar2);
        _views.add(text1);
        _views.add(text2);
        _views.add(button);
        return new ArrayList<View>(){{
            add(seekBar1);
            add(seekBar2);
            add(text1);
            add(text2);
            add(button);
        }};
    }

    public ArrayList<View> RegisterWidget(StrategyPage page){
        final FloatingLinearLayout layout = CreateStrategyHorizontalScrollView();
        _views.add(layout);
        return new ArrayList<View>(){{
            add(layout);
        }};
    }

    public FloatingUIManager(Context context, Display display, Observer observer){
        WIDTH = display.getWidth();
        HEIGHT = display.getHeight();
        RADIUS = HEIGHT / 5;
        imageButtonRatio = CalcRatio();
        _context = context;
        _wm = (WindowManager) context.getSystemService("window");
        _observer = observer;
        initPage = new InitPage(this);
        settingPage = new SettingPage(this);
        startServicePage = new StartServicePage(this);
        variablePage = new VariablePage(this);
        strategyPage = new StrategyPage(this);
        current = initPage;
    }

    private FloatingImageButton CreateStartButton(){
        int side = getStartButtonPosition();
        FloatingImageButton button = CreateButton(side, side, "start");
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                current.StartService();
                _observer.NotifyStart();
            }
        });
        return button;
    }

    private int getStartButtonPosition(){
        return (int)(RADIUS / Math.sqrt(2));
    }

    private FloatingImageButton CreateStopButton(){
        FloatingImageButton button = CreateButton(
                (int)(WIDTH * Constants.LEFT_TOP_WIDGET_X),
                (int)(HEIGHT * Constants.LEFT_TOP_WIDGET_Y), "stop");
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                current.Init();
                if (ConfigData.solverThread != null) {
                    Thread moribund = ConfigData.solverThread;
                    ConfigData.solverThread = null;
                    moribund.interrupt();
                }
                _observer.NotifyStop();
            }
        });
        return button;
    }

    private FloatingImageButton CreateStrategyButton(){
        FloatingImageButton button = CreateButton(
                RADIUS, 0,
                Constants.IdStringMap.get(ConfigData.StyleName) + "_Button");
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                current.Strategy();
            }
        });
        return button;
    }

    private FloatingLinearLayout CreateStrategyHorizontalScrollView(){
        FloatingLinearLayout layout = new FloatingLinearLayout(_context);
        WindowManager.LayoutParams wmParams = layout.getLayoutParams(
                (int)Constants.LEFT_TOP_WIDGET_X,
                (int)Constants.LEFT_TOP_WIDGET_Y);

        HorizontalScrollView scrollView = new HorizontalScrollView(_context);
        scrollView.addView(getStrategyLinearLayout());
        layout.addView(scrollView);
        _wm.addView(layout, wmParams);
        layout.setVisibility(View.INVISIBLE);
        return layout;
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
                current.Setting();
            }
        });
        return button;
    }

    private FloatingImageButton CreateSettingButton(){
        FloatingImageButton button = CreateButton(
                (int)Constants.LEFT_TOP_WIDGET_X,
                (int)Constants.LEFT_TOP_WIDGET_Y, "setting");
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (current == initPage){
                    current.Setting();
                }else{
                    current.Init();
                }
            }
        });
        return button;
    }

    private FloatingImageButton CreateVariableButton(){
        FloatingImageButton button = CreateButton((int)Constants.LEFT_TOP_WIDGET_X, RADIUS, "variable");
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                current.Variable();
            }
        });
        return button;
    }

    private FloatingSeekBar CreateStepSeekBar(final FloatingTextView text){
        final FloatingSeekBar seekBar = CreateSeekBar(
                (int)(WIDTH * Constants.STEP_SEEK_BAR_X),
                (int)(HEIGHT * Constants.STEP_SEEK_BAR_Y));
        seekBar.setMax(Constants.STEP_SEEK_BAR_MAX);
        seekBar.setProgress(ConfigData.deep);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }
            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                text.setText(String.valueOf(seekBar.getProgress()));
                ConfigData.deep = seekBar.getProgress();
            }
        });
        return seekBar;
    }

    private FloatingTextView CreateStepTextView(){
        FloatingTextView text = CreateTextView(
                (int)(WIDTH * Constants.SEEK_BAR_TEXT_X),
                (int)(HEIGHT * Constants.STEP_SEEK_BAR_Y));
        text.setText(String.valueOf(ConfigData.deep));
        return text;
    }

    private FloatingSeekBar CreateComboSeekBar(final FloatingTextView text){
        final FloatingSeekBar seekBar = CreateSeekBar(
                (int)(WIDTH * Constants.COMBO_SEEK_BAR_X),
                (int)(HEIGHT * Constants.COMBO_SEEK_BAR_Y));
        seekBar.setMax(Constants.COMBO_SEEK_BAR_MAX);
        seekBar.setProgress(Integer.parseInt(ConfigData.maxCombo));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }
            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                text.setText(String.valueOf(seekBar.getProgress()));
                ConfigData.maxCombo = String.valueOf(seekBar.getProgress());
            }
        });
        return seekBar;
    }

    private FloatingTextView CreateComboTextView(){
        FloatingTextView text = CreateTextView(
                (int)(WIDTH * Constants.SEEK_BAR_TEXT_X),
                (int)(HEIGHT * Constants.COMBO_SEEK_BAR_Y));
        text.setText(ConfigData.maxCombo);
        return text;
    }

    private FloatingImageButton CreateReturnButton(){
        FloatingImageButton button = CreateButton(
                (int)Constants.LEFT_TOP_WIDGET_X,
                (int)Constants.LEFT_TOP_WIDGET_Y, "return");
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                current.Setting();
            }
        });
        return button;
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
        current.Init();
    }

    public void StopFloatingUI(){
        for(View v : _views) {
            v.setVisibility(View.INVISIBLE);
        }
        current = initPage;
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
