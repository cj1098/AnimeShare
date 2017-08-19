package cj1098.animeshare.modules;

import javax.inject.Singleton;

import cj1098.animedetails.AnimeDetailsFragment;
import cj1098.animeshare.home.animelist.AnimeListFragment;
import cj1098.animeshare.home.HomeHeadlessFragment;
import cj1098.animeshare.service.AnimeRequestService;
import cj1098.base.BaseActivity;
import cj1098.base.BaseApplication;
import cj1098.animeshare.home.search.SearchActivity;
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

        void inject(SearchActivity searchActivity);

        void inject(AnimeRequestService animeRequestService);

        void inject(AnimeDetailsFragment animeDetailsFragment);
    }
}