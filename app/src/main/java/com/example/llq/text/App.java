package com.example.llq.text;

import android.app.Application;

import org.eteclab.ShareSdks;

/**
 * Created by json on 2016/3/29.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler handler = CrashHandler.getInstance();
        handler.init(this);
        Thread.setDefaultUncaughtExceptionHandler(handler);
        ShareSdks.initShare(getApplicationContext());
    }
}
