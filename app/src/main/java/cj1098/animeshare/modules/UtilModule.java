package cj1098.animeshare.modules;

import android.content.Context;

import javax.inject.Singleton;

import cj1098.animeshare.util.DatabaseUtil;
import cj1098.animeshare.util.XMLUtil;
import dagger.Module;
import dagger.Provides;

/**
 * Created by chris on 12/18/16.
 */
@Module (includes = {
        ContextModule.class
})

public class UtilModule {

    @Provides
    @Singleton
    XMLUtil providesXMLUtil(DatabaseUtil databaseUtil) {
        return new XMLUtil(databaseUtil);
    }

    @Provides
    @Singleton
    DatabaseUtil providesDatabaseUtil(Context context) {
        return new DatabaseUtil(context);
    }

}
