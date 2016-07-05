package com.smartalk.gank;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * 蜗壳退役+面试成功 加个彩蛋！
 * Created by panl on 16/1/4.
 */
public class GankApp extends Application {

    private RefWatcher refWatcher;

    public static RefWatcher getRefWatcher(Context context) {
        GankApp application = (GankApp) context.getApplicationContext();
        return application.refWatcher;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher = LeakCanary.install(this);
        Log.d(this.getClass().getSimpleName(),"Welcome to follow me on Github: https://github.com/Panl");

    }
}
