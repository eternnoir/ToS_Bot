package com.tos_bot.ui.state;

import android.view.View;

import com.tos_bot.ui.FloatingUIManager;

import java.util.ArrayList;

/**
 * Created by Sean.
 */
public class VariablePage extends Page{
    private View _floatStepSeekBar;
    private View _floatComboSeekBar;
    private View _floatStepTextView;
    private View _floatComboTextView;
    private View _floatReturnButton;

    public VariablePage(FloatingUIManager context) {
        super(context);
    }

    @Override
    protected void RegisterWidget() {
        ArrayList<View> views = _context.RegisterWidget(this);
        _floatStepSeekBar = views.get(0);
        _floatComboSeekBar = views.get(1);
        _floatStepTextView = views.get(2);
        _floatComboTextView = views.get(3);
        _floatReturnButton = views.get(4);
    }

    @Override
    public void Init() {
        // do nothing.
    }

    @Override
    public void Setting() {
        _floatStepSeekBar.setVisibility(View.INVISIBLE);
        _floatComboSeekBar.setVisibility(View.INVISIBLE);
        _floatStepTextView.setVisibility(View.INVISIBLE);
        _floatComboTextView.setVisibility(View.INVISIBLE);
        _floatReturnButton.setVisibility(View.INVISIBLE);
        _context.Setting();
    }

    @Override
    public void Variable() {
        _floatStepSeekBar.setVisibility(View.VISIBLE);
        _floatComboSeekBar.setVisibility(View.VISIBLE);
        _floatStepTextView.setVisibility(View.VISIBLE);
        _floatComboTextView.setVisibility(View.VISIBLE);
        _floatReturnButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void Strategy() {
        // do nothing.
    }

    @Override
    public void StartService() {
        // do nothing.
    }
}
