package xyz.winthan.padc_networklayer.data.responses;

import com.google.gson.annotations.SerializedName;
import xyz.winthan.padc_networklayer.data.vos.*;

import java.util.List;

/**
 * Created by winthanhtike on 6/19/17.
 */

public class RestaurantResponse {

    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    @SerializedName("timestamp")
    private String timestamp;

    @SerializedName("restaurants")
    private List<RestaurantVO> restaurantVOList;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public List<RestaurantVO> getRestaurantVOList() {
        return restaurantVOList;
    }
}
