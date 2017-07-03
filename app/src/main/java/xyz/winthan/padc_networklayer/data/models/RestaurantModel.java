package xyz.winthan.padc_networklayer.data.models;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import xyz.winthan.padc_networklayer.data.network.RetrofitDataAgent;
import xyz.winthan.padc_networklayer.data.vos.RestaurantVO;
import xyz.winthan.padc_networklayer.events.DataEvent;

/**
 * Created by winthanhtike on 7/1/17.
 */

public class RestaurantModel {

    private static RestaurantModel objInstance;

    private RestaurantModel(){

        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }

    }

    public static RestaurantModel getInstance(){
        if (objInstance == null){
            objInstance = new RestaurantModel();
        }
        return objInstance;
    }

    public void loadRestaurantList(Context context){
        RetrofitDataAgent.getInstance().loadRestaurant(context);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onDataSaved(DataEvent.RestaurantDataLoadedEvent event){

        RestaurantVO.saveRestaurant(event.getContext(), event.getRestaurantVOList());

    }

}
