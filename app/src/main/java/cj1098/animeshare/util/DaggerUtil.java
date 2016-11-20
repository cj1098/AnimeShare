package cj1098.animeshare.util;

import cj1098.animeshare.modules.ApplicationComponent;
import cj1098.animeshare.modules.ContextModule;
import cj1098.animeshare.modules.DaggerApplicationComponent_MainApplicationComponent;
import cj1098.animeshare.modules.PresenterModule;
import cj1098.base.BaseApplication;

/**
 * Created by chris on 11/20/16.
 */

public class DaggerUtil {

    private static DaggerUtil sInstance;
    private ApplicationComponent.MainApplicationComponent mApplicationComponent;

    private DaggerUtil() {

        if (mApplicationComponent == null) {

            ContextModule contextModule = new ContextModule(BaseApplication.getContext());

            mApplicationComponent = DaggerApplicationComponent_MainApplicationComponent.builder()
                    .contextModule(contextModule)
                    .presenterModule(new PresenterModule())
                    .build();
        }
    }

    public ApplicationComponent.MainApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    //For tests
    public void setApplicationComponent(ApplicationComponent.MainApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }

    public static DaggerUtil getInstance() {

        if (sInstance == null) {
            sInstance = new DaggerUtil();
        }
        return sInstance;
    }
}