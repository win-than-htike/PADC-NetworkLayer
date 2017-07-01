package xyz.winthan.padc_networklayer.events;

import android.content.Context;

import java.util.List;

import xyz.winthan.padc_networklayer.data.vos.RestaurantVO;

/**
 * Created by winthanhtike on 6/20/17.
 */

public class DataEvent {

    public static class RestaurantDataLoadedEvent {

        private List<RestaurantVO> restaurantVOList;

        private Context context;

        public RestaurantDataLoadedEvent(Context context, List<RestaurantVO> restaurantVOList) {
            this.restaurantVOList = restaurantVOList;
            this.context = context;
        }

        public Context getContext() {
            return context;
        }

        public List<RestaurantVO> getRestaurantVOList() {
            return restaurantVOList;
        }
    }

    public static class ErrorLoadedEvent {

        private String message;

        public ErrorLoadedEvent(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

}
