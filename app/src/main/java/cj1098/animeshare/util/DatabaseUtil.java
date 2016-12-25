package cj1098.animeshare.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import javax.inject.Singleton;

import cj1098.animeshare.database.AnimeShareDBContract;
import cj1098.animeshare.userList.AnimeObject;
import cj1098.animeshare.viewmodels.Item;

/**
 * Created by chris on 12/18/16.
 */

@Singleton
public class DatabaseUtil extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Animeshare.db";

    private static final String SQL_CREATE_TABLE = "CREATE TABLE " +
            AnimeShareDBContract.AnimeShareEntry.TABLE_NAME + " (" + AnimeShareDBContract.AnimeShareEntry._ID +
            " INTEGER PRIMARY KEY," + AnimeShareDBContract.AnimeShareEntry.COLUMN_NAME_TITLE + " TEXT,";

    public DatabaseUtil(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void setupInitialAnimeFetchData(List<Item> mIdList) {

    }
}
