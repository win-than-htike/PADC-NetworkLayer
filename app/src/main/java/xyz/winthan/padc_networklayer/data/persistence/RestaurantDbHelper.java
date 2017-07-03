package xyz.winthan.padc_networklayer.data.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by winthanhtike on 6/25/17.
 */

public class RestaurantDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "restaurant.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_RESTAURANT_TABLE = "CREATE TABLE " + RestaurantContract.RestaurantEntry.TABLE_NAME + " (" +
            RestaurantContract.RestaurantEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            RestaurantContract.RestaurantEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
            RestaurantContract.RestaurantEntry.COLUMN_ADDR_SHORT + " TEXT, " +
            RestaurantContract.RestaurantEntry.COLUMN_IMAGE + " TEXT, " +
            RestaurantContract.RestaurantEntry.COLUMN_TOTAL_RATING_COUNT + " INTEGER, " +
            RestaurantContract.RestaurantEntry.COLUMN_AVDERAGE_RATING_VALUE + " REAL, " +
            RestaurantContract.RestaurantEntry.COLUMN_IS_ADS + " INTEGER NOT NULL, " +
            RestaurantContract.RestaurantEntry.COLUMN_IS_NEW + " INTEGER NOT NULL, " +
            RestaurantContract.RestaurantEntry.COLUMN_LEAD_TIME_IN_MIN + " INTEGER NOT NULL, " +

            " UNIQUE (" + RestaurantContract.RestaurantEntry.COLUMN_TITLE + ") ON CONFLICT IGNORE" +
            " );";

    private static final String SQL_CREATE_RESTAURANT_TAG_TABLE = "CREATE TABLE " + RestaurantContract.TagEntry.TABLE_NAME + " (" +
            RestaurantContract.TagEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            RestaurantContract.TagEntry.COLUMN_RESTAURNAT_TITLE + " TEXT NOT NULL, " +
            RestaurantContract.TagEntry.COLUMN_TAG + " TEXT NOT NULL, " +

            " UNIQUE (" + RestaurantContract.TagEntry.COLUMN_RESTAURNAT_TITLE + ", " +
            RestaurantContract.TagEntry.COLUMN_TAG + ") ON CONFLICT IGNORE" +
            " );";

    public RestaurantDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_RESTAURANT_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_RESTAURANT_TAG_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RestaurantContract.RestaurantEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RestaurantContract.TagEntry.TABLE_NAME);
    }
}
