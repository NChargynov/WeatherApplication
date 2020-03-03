package com.geektech.weatherapplication;

import android.app.Application;

import com.geektech.weatherapplication.data.PreferenceHelper;

public class WeatherApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new PreferenceHelper(this);
    }
}
