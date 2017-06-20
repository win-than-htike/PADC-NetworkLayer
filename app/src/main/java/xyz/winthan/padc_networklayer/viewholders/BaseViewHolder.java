package xyz.winthan.padc_networklayer.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

/**
 * Created by winthanhtike on 6/20/17.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected boolean mDetechedFromWindow;

    public BaseViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(this);

        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }

        itemView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                mDetechedFromWindow = false;
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                mDetechedFromWindow = true;
            }
        });
    }

    public abstract void bind(T data);

    @Subscribe
    public void onEvent(Object obj) {

    }

    @Override
    public void onClick(View view) {

    }

}
