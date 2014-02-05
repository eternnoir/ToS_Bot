package com.tos_bot;

import java.util.LinkedHashMap;

/**
 * Created by Sean.
 */
public class Constants {
    public static final LinkedHashMap<Integer, String> IdStringMap = new LinkedHashMap<Integer, String>() {
        {
            put(R.id.Vary_color_Single, "Vary_color_Single");
            put(R.id.Vary_color_Multi, "Vary_color_Multi");
            put(R.id.Water_Single, "Water_Single");
            put(R.id.Water_Multi, "Water_Multi");
            put(R.id.Fire_Single, "Fire_Single");
            put(R.id.Fire_Multi, "Fire_Multi");
            put(R.id.Wood_Single, "Wood_Single");
            put(R.id.Wood_Multi, "Wood_Multi");
            put(R.id.Light_Single, "Light_Single");
            put(R.id.Light_Multi, "Light_Multi");
            put(R.id.Dark_Single, "Dark_Single");
            put(R.id.Dark_Multi, "Dark_Multi");
            put(R.id.Recover_Single, "Recover_Single");
            put(R.id.Recover_Multi, "Recover_Multi");
            put(R.id.Water_Except, "Water_Except");
            put(R.id.Fire_Except, "Fire_Except");
            put(R.id.Wood_Except, "Wood_Except");
            put(R.id.Light_Except, "Light_Except");
            put(R.id.Dark_Except, "Dark_Except");
            put(R.id.Recover_Except, "Recover_Except");
            put(R.id.Low_HP_Single, "Low_HP_Single");
            put(R.id.Low_HP_Multi, "Low_HP_Multi");
        }
    };

    public static final int STANDARD_X = 720;
    public static final int STANDARD_Y = 1280;
    public static final double LEFT_TOP_WIDGET_X = 0;
    public static final double LEFT_TOP_WIDGET_Y = 0;
    public static final double SEEK_BAR_WIDTH = 2D / 3D;
    public static final double STEP_SEEK_BAR_X = 1D / 5D;
    public static final double STEP_SEEK_BAR_Y = 0;
    public static final int STEP_SEEK_BAR_MAX = 100;
    public static final double COMBO_SEEK_BAR_X = STEP_SEEK_BAR_X;
    public static final double COMBO_SEEK_BAR_Y = 1D / 12D;
    public static final int COMBO_SEEK_BAR_MAX = 8;
    public static final double SEEK_BAR_TEXT_X = STEP_SEEK_BAR_X + SEEK_BAR_WIDTH;
    public static final int SEEK_BAR_TEXT_SIZE = 20;
}
