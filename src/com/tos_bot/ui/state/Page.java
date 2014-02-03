package com.tos_bot.ui.state;

import com.tos_bot.ui.FloatingUIManager;

/**
 * Created by Sean.
 */
public abstract class Page implements State {
    protected FloatingUIManager _context;
    public Page(FloatingUIManager context){
        _context = context;
        RegisterWidget();
    }
    protected abstract void RegisterWidget();

    public abstract void Init();
    public abstract void Setting();
    public abstract void Variable();
    public abstract void Strategy();
    public abstract void StartService();
}
