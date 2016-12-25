package cj1098.animeshare.database;

import android.provider.BaseColumns;

/**
 * Created by chris on 12/24/16.
 */

public final class AnimeShareDBContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private AnimeShareDBContract() {
    }

    /* Inner class that defines the table contents */
    public static class AnimeShareEntry implements BaseColumns {
        public static final String TABLE_NAME = "anime";
        public static final String COLUMN_NAME_TITLE = "title";

    }

}
