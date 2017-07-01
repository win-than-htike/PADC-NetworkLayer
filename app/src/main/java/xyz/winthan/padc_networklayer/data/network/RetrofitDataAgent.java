package xyz.winthan.padc_networklayer.data.network;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.winthan.padc_networklayer.data.responses.RestaurantResponse;
import xyz.winthan.padc_networklayer.events.DataEvent;
import xyz.winthan.padc_networklayer.utils.RestaurantConstants;

/**
 * Created by winthanhtike on 6/19/17.
 */

public class RetrofitDataAgent implements RestaurantDataAgent{

    private static RetrofitDataAgent objInstance;

    private RestaurantApi mService;

    private RetrofitDataAgent(){

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RestaurantConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        mService = retrofit.create(RestaurantApi.class);

    }

    public static RetrofitDataAgent getInstance(){

        if (objInstance == null){
            objInstance = new RetrofitDataAgent();
        }

        return objInstance;

    }


    @Override
    public void loadRestaurant(final Context context) {

        Call<RestaurantResponse> call = mService.loadRestaurant();
        call.enqueue(new Callback<RestaurantResponse>() {
            @Override
            public void onResponse(Call<RestaurantResponse> call, Response<RestaurantResponse> response) {

                RestaurantResponse restaurantResponse = response.body();
                if (restaurantResponse == null){

                    EventBus.getDefault().post(new DataEvent.ErrorLoadedEvent("Response object is null"));

                }else {

                    EventBus.getDefault().post(new DataEvent.RestaurantDataLoadedEvent(context, restaurantResponse.getRestaurantVOList()));

                }

            }

            @Override
            public void onFailure(Call<RestaurantResponse> call, Throwable t) {
                EventBus.getDefault().post(new DataEvent.ErrorLoadedEvent(t.getMessage()));
            }
        });

    }
}
