package cj1098.animeshare;


import android.app.Application;

/**
 * a global singleton class for maintaining data.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    protected void initSingletons() {
    }
}