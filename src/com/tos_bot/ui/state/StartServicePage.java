package com.tos_bot.ui.state;

import android.view.View;

import com.tos_bot.ui.FloatingUIManager;

import java.util.ArrayList;

/**
 * Created by Sean.
 */
public class StartServicePage extends Page{
    private View _floatStopButton;

    public StartServicePage(FloatingUIManager context) {
        super(context);
    }

    @Override
    protected void RegisterWidget() {
        ArrayList<View> views = _context.RegisterWidget(this);
        _floatStopButton = views.get(0);
    }

    @Override
    public void Init() {
        _floatStopButton.setVisibility(View.INVISIBLE);
        _context.Init();
    }

    @Override
    public void Setting() {
        // do nothing.
    }

    @Override
    public void Variable() {
        // do nothing.
    }

    @Override
    public void Strategy() {
        // do nothing.
    }

    @Override
    public void StartService() {
        _floatStopButton.setVisibility(View.VISIBLE);
    }
}
