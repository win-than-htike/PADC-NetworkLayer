package xyz.winthan.padc_networklayer.mvp.views;

import java.util.List;

import xyz.winthan.padc_networklayer.data.vos.RestaurantVO;

/**
 * Created by winthanhtike on 7/3/17.
 */

public interface RestaurantListView {

    void displayRestaurantList(List<RestaurantVO> restaurantVOList);

    void displayFailToLoadError(String message);

    boolean isEmptyRestaurantList();

}
