package xyz.winthan.padc_networklayer.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import xyz.winthan.padc_networklayer.R;
import xyz.winthan.padc_networklayer.data.vos.RestaurantVO;
import xyz.winthan.padc_networklayer.viewholders.RestaurantViewHolder;

/**
 * Created by winthanhtike on 6/20/17.
 */

public class RestaurantRvAdapter extends BaseRecyclerAdapter<RestaurantViewHolder, RestaurantVO> {

    public RestaurantRvAdapter(Context context) {
        super(context);
    }

    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_restaurant, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RestaurantViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }
}
