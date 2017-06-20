package xyz.winthan.padc_networklayer.data.vos;

import com.google.gson.annotations.SerializedName;

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
}
