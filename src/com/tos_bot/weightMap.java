package com.tos_bot;

import java.util.LinkedHashMap;

public class weightMap {
    private static weightMap ourInstance = new weightMap();
    private final LinkedHashMap<String, String> weightMap = new LinkedHashMap<String, String>() {
        {
            put("5-color, Single",  "1,1,1,1,1,1,1,1,1,1,0.3,0.3");
            put("5-color, Multi",   "1,3,1,3,1,3,1,3,1,3,0.3,0.3");
            put("Water, Single",    "1,1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.3,0.3");
            put("Water, Multi",     "1,3,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.3,0.3");
            put("Fire, Single",     "0.1,0.1,1,1,0.1,0.1,0.1,0.1,0.1,0.1,0.3,0.3");
            put("Fire, Multi",      "0.1,0.1,1,3,0.1,0.1,0.1,0.1,0.1,0.1,0.3,0.3");
            put("Wood, Single",     "0.1,0.1,0.1,0.1,1,1,0.1,0.1,0.1,0.1,0.3,0.3");
            put("Wood, Multi",      "0.1,0.1,0.1,0.1,1,3,0.1,0.1,0.1,0.1,0.3,0.3");
            put("Light, Single",    "0.1,0.1,0.1,0.1,0.1,0.1,1,1,0.1,0.1,0.3,0.3");
            put("Light, Multi",     "0.1,0.1,0.1,0.1,0.1,0.1,1,3,0.1,0.1,0.3,0.3");
            put("Dark, Single",     "0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,1,1,0.3,0.3");
            put("Dark, Multi",      "0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,1,3,0.3,0.3");
            put("Recover, Single",  "0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,1,1");
            put("Recover, Multi",   "0.1,0.3,0.1,0.3,0.1,0.3,0.1,0.3,0.1,0.3,1,1");
        }
    };

    public static weightMap getInstance() {
        return ourInstance;
    }

    public String[] getStyleList() {
        return weightMap.keySet().toArray(new String[0]);
    }

    public String getWeight(String key) {
        return weightMap.get(key);
    }
}
