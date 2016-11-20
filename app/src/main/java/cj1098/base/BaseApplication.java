package cj1098.base;


import android.app.Application;
import android.content.Context;

import cj1098.animeshare.util.DaggerUtil;

/**
 * a global singleton class for maintaining data.
 */
public class BaseApplication extends Application {
    protected static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;

        DaggerUtil.getInstance().getApplicationComponent().inject(this);
    }

    public static Context getContext() {
        return sContext;
    }
}