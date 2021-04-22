package com.sunnyweather.android;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class SunnyWeatherApplication extends Application {
    @SuppressLint("staticFieldLeak")
    static Context context;
    public static final String TOKEN = "enTOD9vQllHD5wqV";

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
