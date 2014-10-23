package com.tos_bot.ui;

import com.tos_bot.MainActivity;

/**
 * Created by Sean.
 */
public class Observer {
    private MainActivity _acticity;

    public Observer(MainActivity acticity){
        _acticity = acticity;
    }

    public void NotifyStart(){
        _acticity.StartService();
    }

    public void NotifyStop(){
        _acticity.StopService();
    }
}
