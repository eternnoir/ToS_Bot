package com.tos_bot.ui.state;

import android.view.View;

import com.tos_bot.ui.FloatingUIManager;

import java.util.ArrayList;

/**
 * Created by Sean.
 */
public class InitPage extends Page {
    private View _floatSettingButton;

    public InitPage(FloatingUIManager context) {
        super(context);
    }

    @Override
    protected void RegisterWidget() {
        ArrayList<View> views = _context.RegisterWidget(this);
        _floatSettingButton = views.get(0);
    }

    @Override
    public void Init() {
        _floatSettingButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void Setting() {
        _floatSettingButton.setVisibility(View.INVISIBLE);
        _context.Setting();
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
        // do nothing.
    }
}
