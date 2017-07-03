package xyz.winthan.padc_networklayer.mvp.presenters;

import android.content.Context;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import xyz.winthan.padc_networklayer.data.models.RestaurantModel;
import xyz.winthan.padc_networklayer.data.vos.RestaurantVO;
import xyz.winthan.padc_networklayer.events.DataEvent;
import xyz.winthan.padc_networklayer.mvp.views.RestaurantListView;

/**
 * Created by winthanhtike on 7/3/17.
 */

public class RestaurantListPresenter extends BasePresenter {

    private RestaurantListView mView;
    private Context context;

    public RestaurantListPresenter(Context context, RestaurantListView mView) {
        this.mView = mView;
        this.context = context;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    public void loadRestaurantFromDb(List<RestaurantVO> restaurantVOList){
        mView.displayRestaurantList(restaurantVOList);
    }

    public void loadRestaurantData() {
        RestaurantModel.getInstance().loadRestaurantList(context);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ErrorLoadedEvent(DataEvent.ErrorLoadedEvent event){
        mView.displayFailToLoadError(event.getMessage());
    }

}
