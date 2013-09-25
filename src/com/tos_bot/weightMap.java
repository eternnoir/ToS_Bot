package com.tos_bot;

import java.util.LinkedHashMap;

public class weightMap {
    private static weightMap ourInstance = new weightMap();
    private final LinkedHashMap<Integer, String> weightMap = new LinkedHashMap<Integer, String>() {
        {
            put(R.id.Vary_color_Single,    "0,0,3,1,3,1,3,1,3,1,3,1,3,1");
            put(R.id.Vary_color_Multi,     "0,0,1,3,1,3,1,3,1,3,1,3,1,3");
            put(R.id.Water_Single,         "0,0,3,1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.3,0.3");
            put(R.id.Water_Multi,          "0,0,1,3,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.3,0.3");
            put(R.id.Water_Except,         "0,0,-1,-1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.3,0.3");
            put(R.id.Fire_Single,          "0,0,0.1,0.1,3,1,0.1,0.1,0.1,0.1,0.1,0.1,0.3,0.3");
            put(R.id.Fire_Multi,           "0,0,0.1,0.1,1,3,0.1,0.1,0.1,0.1,0.1,0.1,0.3,0.3");
            put(R.id.Fire_Except,          "0,0,0.1,0.1,-1,-1,0.1,0.1,0.1,0.1,0.1,0.1,0.3,0.3");
            put(R.id.Wood_Single,          "0,0,0.1,0.1,0.1,0.1,3,1,0.1,0.1,0.1,0.1,0.3,0.3");
            put(R.id.Wood_Multi,           "0,0,0.1,0.1,0.1,0.1,1,3,0.1,0.1,0.1,0.1,0.3,0.3");
            put(R.id.Wood_Except,          "0,0,0.1,0.1,0.1,0.1,-1,-1,0.1,0.1,0.1,0.1,0.3,0.3");
            put(R.id.Light_Single,         "0,0,0.1,0.1,0.1,0.1,0.1,0.1,3,1,0.1,0.1,0.3,0.3");
            put(R.id.Light_Multi,          "0,0,0.1,0.1,0.1,0.1,0.1,0.1,1,3,0.1,0.1,0.3,0.3");
            put(R.id.Light_Except,         "0,0,0.1,0.1,0.1,0.1,0.1,0.1,-1,-1,0.1,0.1,0.3,0.3");
            put(R.id.Dark_Single,          "0,0,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,3,1,0.3,0.3");
            put(R.id.Dark_Multi,           "0,0,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,1,3,0.3,0.3");
            put(R.id.Dark_Except,          "0,0,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,-1,-1,0.3,0.3");
            put(R.id.Recover_Single,       "0,0,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,1,1");
            put(R.id.Recover_Multi,        "0,0,0.1,0.3,0.1,0.3,0.1,0.3,0.1,0.3,0.1,0.3,1,1");
            put(R.id.Recover_Except,       "0,0,3,1,3,1,3,1,3,1,3,1,-1,-1");
            put(R.id.Low_HP_Single,        "0,0,3,1,3,1,3,1,3,1,3,1,-0.1,-0.1");
            put(R.id.Low_HP_Multi,         "0,0,1,3,1,3,1,3,1,3,1,3,-0.1,-0.1");
        }
    };

    public static weightMap getInstance() {
        return ourInstance;
    }

    public Integer[] getStyleList() {
        return weightMap.keySet().toArray(new Integer[0]);
    }

    public String[] getStyleListString() {
        return weightMap.keySet().toArray(new String[0]);
    }

    public String getWeight(Integer key) {
        return weightMap.get(key);
    }
}
