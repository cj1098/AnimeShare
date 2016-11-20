package cj1098.animeshare.modules;

import javax.inject.Singleton;

import cj1098.animeshare.home.HomeHeadlessFragment;
import cj1098.animeshare.home.HomeHeadlessMvp;
import cj1098.base.BaseActivity;
import cj1098.base.BaseApplication;
import dagger.Component;

public class ApplicationComponent {

    @Singleton
    @Component(modules = {
            ContextModule.class,
            PresenterModule.class,
            DeviceModule.class,
    })
    public interface MainApplicationComponent {

        void inject(HomeHeadlessFragment homeHeadlessFragment);

        void inject(BaseApplication baseApplication);

        void inject(BaseActivity baseActivity);
    }
}