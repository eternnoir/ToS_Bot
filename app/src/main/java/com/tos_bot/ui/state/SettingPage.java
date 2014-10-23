package com.tos_bot.ui.state;

import android.view.View;

import com.tos_bot.ui.FloatingUIManager;

import java.util.ArrayList;

/**
 * Created by Sean.
 */
public class SettingPage extends Page{
    private View _floatStartButton;
    private View _floatStrategyButton;
    private View _floatSettingButton;
    private View _floatVariableButton;

    public SettingPage(FloatingUIManager context) {
        super(context);
    }

    private void setAllButtonVisibility(int visibility){
        _floatSettingButton.setVisibility(visibility);
        _floatVariableButton.setVisibility(visibility);
        _floatStrategyButton.setVisibility(visibility);
        _floatStartButton.setVisibility(visibility);
    }

    @Override
    protected void RegisterWidget() {
        ArrayList<View> views = _context.RegisterWidget(this);
        _floatStartButton = views.get(0);
        _floatStrategyButton = views.get(1);
        _floatVariableButton = views.get(2);
        _floatSettingButton = views.get(3);
    }

    @Override
    public void Init() {
        setAllButtonVisibility(View.INVISIBLE);
        _context.Init();
    }

    @Override
    public void Setting() {
        setAllButtonVisibility(View.VISIBLE);
    }

    @Override
    public void Variable() {
        setAllButtonVisibility(View.INVISIBLE);
        _context.Variable();
    }

    @Override
    public void Strategy() {
        setAllButtonVisibility(View.INVISIBLE);
        _context.Strategy();
    }

    @Override
    public void StartService() {
        setAllButtonVisibility(View.INVISIBLE);
        _context.StartService();
    }
}
