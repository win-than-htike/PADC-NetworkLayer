package xyz.winthan.padc_networklayer.data.network;

import retrofit2.Call;
import retrofit2.http.GET;
import xyz.winthan.padc_networklayer.data.responses.RestaurantResponse;

/**
 * Created by winthanhtike on 6/19/17.
 */

public interface RestaurantApi {

    @GET("get-restaurants.php")
    Call<RestaurantResponse> loadRestaurant();

}
