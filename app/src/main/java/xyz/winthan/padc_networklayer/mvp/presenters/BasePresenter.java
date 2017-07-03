package xyz.winthan.padc_networklayer.mvp.presenters;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by winthanhtike on 7/3/17.
 */

public abstract class BasePresenter {

    public void onCreate() {
        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    public abstract void onStart();

    public abstract void onStop();

    public void onDestory() {
        EventBus.getDefault().unregister(this);
    }

}
