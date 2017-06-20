package xyz.winthan.padc_networklayer.activities;

import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by winthanhtike on 6/20/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();

        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();

        EventBus.getDefault().unregister(this);

    }
}
