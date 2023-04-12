package com.example.gpsapp;

import android.app.Application;
import android.content.Context;

public class GetCurrent extends Application {
    private static Context contextStack;
    public void onCreate() {

        super.onCreate();
        GetCurrent.contextStack = getApplicationContext();
    }
    public static Context GetContext(){
        return GetCurrent.contextStack;
    }
}
