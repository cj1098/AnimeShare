package cj1098.animeshare.modules;

import javax.inject.Singleton;

import cj1098.animeshare.service.AnimeRequestService;
import dagger.Module;
import dagger.Provides;

/**
 * Created by chris on 12/27/16.
 */
@Module
public class ServiceModule {

    @Provides
    @Singleton
    AnimeRequestService providesAnimeRequestService() {
        return new AnimeRequestService();
    }
}
