package com.tos_bot;

import java.util.LinkedHashMap;

public class ComboWeightMap {
    private static ComboWeightMap ourInstance = new ComboWeightMap();

    private final LinkedHashMap<Integer, String> MaxComboWeightMap = new LinkedHashMap<Integer, String>() {
        {
			put(R.MaxCombo.Combo_0, "0");
			put(R.MaxCombo.Combo_1, "1");
			put(R.MaxCombo.Combo_2, "2");
			put(R.MaxCombo.Combo_3, "3");
			put(R.MaxCombo.Combo_4, "4");
			put(R.MaxCombo.Combo_5, "5");
			put(R.MaxCombo.Combo_6, "6");
        }
    };
    public static ComboWeightMap getInstance() {
        return ourInstance;
    }

    public Integer[] getStyleList() {
        return MaxComboWeightMap.keySet().toArray(new Integer[0]);
    }

    public String[] getStyleListString() {
        return MaxComboWeightMap.keySet().toArray(new String[0]);
    }

    public String getWeight(Integer key) {
        return MaxComboWeightMap.get(key);
    }
}
