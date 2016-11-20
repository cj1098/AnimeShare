package cj1098.animeshare.modules;

import android.content.Context;

import javax.inject.Singleton;

import cj1098.animeshare.util.DeviceUtil;
import dagger.Module;
import dagger.Provides;

/**
 * Created by chris on 11/20/16.
 */

@Module (includes = {
        ContextModule.class
})
public class DeviceModule {

    @Provides
    @Singleton
    DeviceUtil providesDeviceUtil(Context context) {
        return new DeviceUtil(context);
    }
}
