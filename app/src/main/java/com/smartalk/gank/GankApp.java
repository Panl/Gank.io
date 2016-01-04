package com.smartalk.gank;

import android.app.Application;

import im.fir.sdk.FIR;

/**
 * Created by panl on 16/1/4.
 */
public class GankApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FIR.init(this);
    }
}
