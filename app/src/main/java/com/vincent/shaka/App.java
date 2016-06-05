package com.vincent.shaka;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

/**
 * Created by mowen on 5/9/16.
 */
public class App extends Application {

    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

//        GlobalEnv.getGlobalEnv().setHostDebug("http://172.16.0.174:5000/");

        Stetho.initializeWithDefaults(this);
    }

    public static Context getAppContext() {
        return mContext;
    }
}
