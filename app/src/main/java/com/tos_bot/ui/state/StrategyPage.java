package com.tos_bot.ui.state;

import android.view.View;

import com.tos_bot.ui.FloatingUIManager;

import java.util.ArrayList;

/**
 * Created by Sean.
 */
public class StrategyPage extends Page{
    private View _floatStrategyLayout;

    public StrategyPage(FloatingUIManager context) {
        super(context);
    }

    @Override
    protected void RegisterWidget() {
        ArrayList<View> views = _context.RegisterWidget(this);
        _floatStrategyLayout = views.get(0);
    }

    @Override
    public void Init() {
        // do nothing.
    }

    @Override
    public void Setting() {
        _floatStrategyLayout.setVisibility(View.INVISIBLE);
        _context.Setting();
    }

    @Override
    public void Variable() {
        // do nothing.
    }

    @Override
    public void Strategy() {
        _floatStrategyLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void StartService() {
        // do nothing.
    }
}
