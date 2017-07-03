package xyz.winthan.padc_networklayer.data.vos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import xyz.winthan.padc_networklayer.data.persistence.RestaurantContract;

/**
 * Created by winthanhtike on 6/19/17.
 */

public class RestaurantVO {

    @SerializedName("title")
    private String title;

    @SerializedName("addr-short")
    private String addrShort;

    @SerializedName("image")
    private String image;

    @SerializedName("total-rating-count")
    private int totalRatingCount;

    @SerializedName("average-rating-value")
    private float averageRatingValue;

    @SerializedName("is-ad")
    private boolean isAd;

    @SerializedName("is-new")
    private boolean isNew;

    @SerializedName("tags")
    private String[] tags;

    @SerializedName("lead-time-in-min")
    private int leadTimeinMin;

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public String getAddrShort() {
        return addrShort;
    }

    public String getImage() {
        return image;
    }

    public int getTotalRatingCount() {
        return totalRatingCount;
    }

    public float getAverageRatingValue() {
        return averageRatingValue;
    }

    public boolean isAd() {
        return isAd;
    }

    public boolean isNew() {
        return isNew;
    }

    public String[] getTags() {
        return tags;
    }

    public int getLeadTimeinMin() {
        return leadTimeinMin;
    }

    public static void saveRestaurant(Context context, List<RestaurantVO> restaurantVOList){

        ContentValues[] restaurantCVs = new ContentValues[restaurantVOList.size()];

        for (int index = 0; index < restaurantVOList.size(); index++){
            RestaurantVO restaurantVO = restaurantVOList.get(index);
            restaurantCVs[index] = restaurantVO.parseToContentValue();

            RestaurantVO.saveRestaurantTags(context, restaurantVO.getTitle(), restaurantVO.getTags());

        }

        if (context != null){
            int insertedCount = context.getContentResolver().bulkInsert(RestaurantContract.RestaurantEntry.CONTENT_URI, restaurantCVs);
            Log.d("RESTAURNAT INSERTING =>", "Bulk inserted into attraction table : " + insertedCount);
        }

    }

    private static void saveRestaurantTags (Context context, String title, String[] tags) {

        ContentValues[] tagCVs = new ContentValues[tags.length];
        for (int index = 0; index < tags.length; index++) {

            String tag = tags[index];
            ContentValues values = new ContentValues();
            values.put(RestaurantContract.TagEntry.COLUMN_RESTAURNAT_TITLE, title);
            values.put(RestaurantContract.TagEntry.COLUMN_TAG, tag);

            tagCVs[index] = values;

        }

        if (context != null){
            context.getContentResolver().bulkInsert(RestaurantContract.TagEntry.CONTENT_URI, tagCVs);
        }

    }

    private ContentValues parseToContentValue(){

        ContentValues values = new ContentValues();
        values.put(RestaurantContract.RestaurantEntry.COLUMN_TITLE, title);
        values.put(RestaurantContract.RestaurantEntry.COLUMN_ADDR_SHORT, addrShort);
        values.put(RestaurantContract.RestaurantEntry.COLUMN_IMAGE, image);
        values.put(RestaurantContract.RestaurantEntry.COLUMN_TOTAL_RATING_COUNT, totalRatingCount);
        values.put(RestaurantContract.RestaurantEntry.COLUMN_AVDERAGE_RATING_VALUE, averageRatingValue);
        values.put(RestaurantContract.RestaurantEntry.COLUMN_IS_ADS, isAd);
        values.put(RestaurantContract.RestaurantEntry.COLUMN_IS_NEW, isNew);
        values.put(RestaurantContract.RestaurantEntry.COLUMN_LEAD_TIME_IN_MIN, leadTimeinMin);
        return values;

    }

    public static RestaurantVO parseFromCursor(Cursor data){

        RestaurantVO restaurantVO = new RestaurantVO();
        restaurantVO.title = data.getString(data.getColumnIndex(RestaurantContract.RestaurantEntry.COLUMN_TITLE));
        restaurantVO.addrShort = data.getString(data.getColumnIndex(RestaurantContract.RestaurantEntry.COLUMN_ADDR_SHORT));
        restaurantVO.image = data.getString(data.getColumnIndex(RestaurantContract.RestaurantEntry.COLUMN_IMAGE));
        restaurantVO.totalRatingCount = data.getInt(data.getColumnIndex(RestaurantContract.RestaurantEntry.COLUMN_TOTAL_RATING_COUNT));
        restaurantVO.averageRatingValue = data.getFloat(data.getColumnIndex(RestaurantContract.RestaurantEntry.COLUMN_AVDERAGE_RATING_VALUE));
        restaurantVO.isAd = data.getInt(data.getColumnIndex(RestaurantContract.RestaurantEntry.COLUMN_IS_ADS)) > 0;
        restaurantVO.isNew = data.getInt(data.getColumnIndex(RestaurantContract.RestaurantEntry.COLUMN_IS_NEW)) > 0;
        restaurantVO.leadTimeinMin = data.getInt(data.getColumnIndex(RestaurantContract.RestaurantEntry.COLUMN_LEAD_TIME_IN_MIN));
        return restaurantVO;

    }

    public static String[] loadRestaurantTagByTitle(Context context, String title) {

        ArrayList<String> tags = new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(RestaurantContract.TagEntry.buildRestaurantUriWithTitle(title),
                null, null, null, null);

        if(cursor != null && cursor.moveToFirst()) {

            do {

                tags.add(cursor.getString(cursor.getColumnIndex(RestaurantContract.TagEntry.COLUMN_TAG)));

            } while (cursor.moveToNext());

        }

        String[] tagArray = new String[tags.size()];
        tags.toArray(tagArray);
        return tagArray;

    }

}
