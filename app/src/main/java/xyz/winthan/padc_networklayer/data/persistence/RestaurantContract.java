package xyz.winthan.padc_networklayer.data.persistence;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import xyz.winthan.padc_networklayer.App;

/**
 * Created by winthanhtike on 6/25/17.
 */

public class RestaurantContract {

    public static final String CONTENT_AUTHORITY = App.class.getPackage().getName();
    public static final Uri BASE_CONTETN_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_RESTAURANT = "restaurants";
    public static final String PATH_TAG = "tags";

    public static class RestaurantEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTETN_URI.buildUpon().appendPath(PATH_RESTAURANT).build();

        public static final String DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RESTAURANT;

        public static final String ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RESTAURANT;

        public static final String TABLE_NAME = PATH_RESTAURANT;
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_ADDR_SHORT = "addr_short";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_TOTAL_RATING_COUNT = "total_rating_count";
        public static final String COLUMN_AVDERAGE_RATING_VALUE = "avderage_rating_value";
        public static final String COLUMN_IS_ADS = "is_ads";
        public static final String COLUMN_IS_NEW = "is_new";
        public static final String COLUMN_LEAD_TIME_IN_MIN = "lead_time_in_min";

        public static Uri buildRestaurantUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildRestaurantUriWithTitle(String restaurantTitle){
            return CONTENT_URI.buildUpon().appendQueryParameter(COLUMN_TITLE, restaurantTitle).build();
        }

        public static String getTitleFromParams(Uri uri){
            return uri.getQueryParameter(COLUMN_TITLE);
        }

    }

    public static class TagEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTETN_URI.buildUpon().appendPath(PATH_TAG).build();

        public static final String DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TAG;

        public static final String ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TAG;

        public static final String TABLE_NAME = PATH_TAG;
        public static final String COLUMN_RESTAURNAT_TITLE = "restaurant_title";
        public static final String COLUMN_TAG = "tag";

        public static Uri buildRestaurantUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildRestaurantUriWithTitle(String restaurantTitle){
            return CONTENT_URI.buildUpon().appendQueryParameter(COLUMN_RESTAURNAT_TITLE, restaurantTitle).build();
        }

        public static String getTitleFromParams(Uri uri){
            return uri.getQueryParameter(COLUMN_RESTAURNAT_TITLE);
        }

    }

}
