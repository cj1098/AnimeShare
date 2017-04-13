package cj1098.animeshare.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import javax.inject.Singleton;

import cj1098.animeshare.database.AnimeShareDBContract;
import cj1098.animeshare.userList.SmallAnimeObject;
import cj1098.event.InitialDatabaseStorageEventEnded;
import cj1098.event.RxBus;

/**
 * Created by chris on 12/18/16.
 */

@Singleton
public class DatabaseUtil extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Animeshare.db";
    private static final String MAX_BATCH_AMOUNT = "50";

    private RxBus mRxBus;

    private static final String SQL_CREATE_TABLE = "CREATE TABLE " +
            AnimeShareDBContract.AnimeShareEntry.TABLE_NAME + " (" + AnimeShareDBContract.AnimeShareEntry._ID +
            " INTEGER PRIMARY KEY," + AnimeShareDBContract.AnimeShareEntry.COLUMN_NAME_TITLE + " TEXT," +
            AnimeShareDBContract.AnimeShareEntry.COLUMN_NAME_ID + " TEXT)";

    public DatabaseUtil(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mRxBus = RxBus.getInstance();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void setupInitialAnimeFetchData(List<SmallAnimeObject> mIdList) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        for (int i = 0, n = mIdList.size(); i < n; i++) {
            values.put(AnimeShareDBContract.AnimeShareEntry.COLUMN_NAME_TITLE, mIdList.get(i).getTitleEnglish());
            values.put(AnimeShareDBContract.AnimeShareEntry.COLUMN_NAME_ID, mIdList.get(i).getId());
            db.insert(AnimeShareDBContract.AnimeShareEntry.TABLE_NAME, null, values);
        }
        db.setTransactionSuccessful();
        db.endTransaction();

        InitialDatabaseStorageEventEnded initialDatabaseStorageEventEnded = new InitialDatabaseStorageEventEnded();
        mRxBus.post(initialDatabaseStorageEventEnded);
    }

    public String getNextFiftyIds(int startingId) {
        String itemIds = "";

        String sql = "SELECT * FROM " + AnimeShareDBContract.AnimeShareEntry.TABLE_NAME + " limit " + Integer.toString(startingId) + ", " + MAX_BATCH_AMOUNT;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                itemIds += cursor.getString(cursor.getColumnIndex("id")) + "/";
            }
        }
        cursor.close();

        return itemIds;
    }
}
