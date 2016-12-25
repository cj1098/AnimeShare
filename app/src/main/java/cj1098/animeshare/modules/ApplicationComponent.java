package cj1098.animeshare.modules;

import com.bumptech.glide.util.Util;

import javax.inject.Singleton;

import cj1098.animeshare.animelist.AnimeListFragment;
import cj1098.animeshare.home.HomeHeadlessFragment;
import cj1098.animeshare.service.AnimeRequestService;
import cj1098.base.BaseActivity;
import cj1098.base.BaseApplication;
import dagger.Component;

public class ApplicationComponent {

    @Singleton
    @Component(modules = {
            ContextModule.class,
            PresenterModule.class,
            DeviceModule.class,
            UtilModule.class
    })
    public interface MainApplicationComponent {

        void inject(HomeHeadlessFragment homeHeadlessFragment);

        void inject(AnimeListFragment animeListFragment);

        void inject(BaseApplication baseApplication);

        void inject(BaseActivity baseActivity);

        void inject(AnimeRequestService animeRequestService);
    }
}