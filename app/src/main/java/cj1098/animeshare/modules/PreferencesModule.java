package cj1098.animeshare.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import cj1098.animeshare.util.Preferences;
import dagger.Module;
import dagger.Provides;

@Module(includes = ContextModule.class)
public class PreferencesModule {

    private static final String PREFS_NAME = "MyPrefs";

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(@NonNull Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    Preferences providesPreferences(@NonNull SharedPreferences sharedPreferences) {
        return new Preferences(sharedPreferences);
    }
}