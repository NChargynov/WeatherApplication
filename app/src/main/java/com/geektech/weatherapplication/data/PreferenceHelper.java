package com.geektech.weatherapplication.data;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {

    private static final String PREFERENCE_NAME = "weather";
    private static final String IS_SHOWN = "isShown";
    private static final String IS_PRESSED = "isPressed";
    private static SharedPreferences preferences;

    public PreferenceHelper(Context context) {
        preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static boolean isPressed() {
        return preferences.getBoolean(IS_PRESSED, false);
    }

    public static void setIsPressed(boolean isPressed){
        preferences.edit().putBoolean(IS_PRESSED, isPressed).apply();
    }


    public static boolean isShown (){
        return preferences.getBoolean(IS_SHOWN, false);
    }

    public static void setIsShown(boolean isShown){
        preferences.edit().putBoolean(IS_SHOWN, isShown).apply();
    }
}
