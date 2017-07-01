package xyz.winthan.padc_networklayer.data.persistence;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;


/**
 * Created by winthanhtike on 6/25/17.
 */

public class RestaurantProvider extends ContentProvider {

    private static final int RESTAURANT = 1;
    private static final int RESTAURANT_TAG = 2;

    private static final String sRestaurantTitleSelection = RestaurantContract.RestaurantEntry.COLUMN_TITLE + " = ?";
    private static final String sRestaurantTagSelectionWithTitle = RestaurantContract.TagEntry.COLUMN_RESTAURNAT_TITLE + " = ?";

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static RestaurantDbHelper mDbHelper;

    private static UriMatcher buildUriMatcher() {

        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(RestaurantContract.CONTENT_AUTHORITY, RestaurantContract.PATH_RESTAURANT, RESTAURANT);
        uriMatcher.addURI(RestaurantContract.CONTENT_AUTHORITY, RestaurantContract.PATH_TAG, RESTAURANT_TAG);

        return uriMatcher;

    }

    @Override
    public boolean onCreate() {

        mDbHelper = new RestaurantDbHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {

        final SQLiteDatabase db = mDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);

        Cursor queryCursor;

        switch (match) {

            case RESTAURANT:

                String restaurantTitle = RestaurantContract.RestaurantEntry.getTitleFromParams(uri);

                if (!TextUtils.isEmpty(restaurantTitle)) {

                    s = sRestaurantTitleSelection;
                    strings = new String[]{restaurantTitle};

                }

                queryCursor = db.query(RestaurantContract.RestaurantEntry.TABLE_NAME,
                        strings,
                        s,
                        strings1,
                        null,
                        null,
                        s1);

                break;

            case RESTAURANT_TAG:

                String title = RestaurantContract.TagEntry.getTitleFromParams(uri);

                if (!TextUtils.isEmpty(title)) {

                    s = sRestaurantTagSelectionWithTitle;
                    strings = new String[]{title};

                }

                queryCursor = db.query(RestaurantContract.TagEntry.TABLE_NAME,
                        strings,
                        s,
                        strings1,
                        null,
                        null,
                        s1);

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);

        }

        Context context = getContext();
        if (context != null) {
            queryCursor.setNotificationUri(context.getContentResolver(), uri);
        }

        return queryCursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        int match = sUriMatcher.match(uri);

        switch (match) {

            case RESTAURANT :
                return RestaurantContract.RestaurantEntry.DIR_TYPE;

            case RESTAURANT_TAG :
                return RestaurantContract.TagEntry.DIR_TYPE;

        }

        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        Uri insertedUri;

        switch (match) {

            case RESTAURANT: {
                long _id = db.insert(RestaurantContract.RestaurantEntry.TABLE_NAME, null, contentValues);
                if (_id > 0 ){
                    insertedUri = RestaurantContract.RestaurantEntry.buildRestaurantUri(_id);
                }else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }

            case RESTAURANT_TAG:{
                long _id = db.insert(RestaurantContract.TagEntry.TABLE_NAME, null, contentValues);
                if (_id > 0 ){
                    insertedUri = RestaurantContract.TagEntry.buildRestaurantUri(_id);
                }else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknow Uri " + uri);

        }

        Context context = getContext();
        if (context != null) {
            context.getContentResolver().notifyChange(uri, null);
        }

        return insertedUri;

    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String tableName = getTableName(uri);
        int insertedCount = 0;

        try {

            db.beginTransaction();
            for (ContentValues cv : values) {
                long _id = db.insert(tableName, null, cv);
                if (_id > 0) {
                    insertedCount++;
                }
            }

            db.setTransactionSuccessful();

        } finally {
            db.endTransaction();
            db.close();
        }

        Context context = getContext();
        if (context != null) {
            context.getContentResolver().notifyChange(uri, null);
        }

        return insertedCount;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int rowDeleted;
        String tableName = getTableName(uri);

        rowDeleted = db.delete(tableName, s, strings);
        Context context = getContext();
        if (context != null && rowDeleted > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return rowDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int rowUpdated;
        String tableName = getTableName(uri);

        rowUpdated = db.update(tableName, contentValues, s, strings);
        Context context = getContext();
        if (context != null && rowUpdated > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return rowUpdated;
    }

    private String getTableName(Uri uri) {
        final int matchUri = sUriMatcher.match(uri);

        switch (matchUri) {
            case RESTAURANT:
                return RestaurantContract.RestaurantEntry.TABLE_NAME;
            case RESTAURANT_TAG:
                return RestaurantContract.TagEntry.TABLE_NAME;

            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }
    }

}
