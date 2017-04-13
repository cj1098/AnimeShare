package cj1098.animeshare.modules;

import javax.inject.Singleton;

import cj1098.animeshare.service.AnimeRequestService;
import cj1098.animeshare.util.Preferences;
import dagger.Module;
import dagger.Provides;

/**
 * Created by chris on 12/27/16.
 */
@Module (includes = PreferencesModule.class
)
public class ServiceModule {

    @Provides
    @Singleton
    AnimeRequestService providesAnimeRequestService(Preferences preferences) {
        return new AnimeRequestService(preferences);
    }
}
