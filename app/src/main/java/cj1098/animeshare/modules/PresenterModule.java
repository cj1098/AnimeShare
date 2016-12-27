package cj1098.animeshare.modules;

import cj1098.animeshare.animelist.AnimeListMvp;
import cj1098.animeshare.animelist.AnimeListPresenter;
import cj1098.animeshare.home.HomeHeadlessMvp;
import cj1098.animeshare.home.HomeHeadlessPresenter;
import cj1098.animeshare.util.DatabaseUtil;
import cj1098.animeshare.util.DeviceUtil;
import cj1098.animeshare.util.XMLUtil;
import dagger.Module;
import dagger.Provides;

/**
 * Created by chris on 11/20/16.
 */

@Module (includes = {
        DeviceModule.class,
        UtilModule.class
})
public class PresenterModule {

    @Provides
    HomeHeadlessMvp.Presenter providesHomeHeadlessPresenter(DeviceUtil deviceUtil, DatabaseUtil databaseUtil, XMLUtil xmlUtil) {
        return new HomeHeadlessPresenter(deviceUtil, databaseUtil, xmlUtil);
   }

    @Provides
    AnimeListMvp.Presenter providesAnimeListPresenter(DatabaseUtil databaseUtil, XMLUtil xmlUtil) {
        return new AnimeListPresenter(databaseUtil, xmlUtil);
    }
}
