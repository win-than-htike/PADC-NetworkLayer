package xyz.winthan.padc_networklayer.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import xyz.winthan.padc_networklayer.R;
import xyz.winthan.padc_networklayer.data.vos.RestaurantVO;

/**
 * Created by winthanhtike on 6/20/17.
 */

public class RestaurantViewHolder extends BaseViewHolder<RestaurantVO> {

    @BindView(R.id.iv_ads)
    ImageView ivAds;

    @BindView(R.id.iv_restaurant)
    ImageView ivRestaurant;

    @BindView(R.id.tv_restaurant_title)
    TextView tvRestaurantTitle;

    @BindView(R.id.tv_deliver_time)
    TextView tvDeliverTime;

    @BindView(R.id.rb_restaurant_rating)
    RatingBar rbRestaurantRating;

    @BindView(R.id.tv_total_rating_count)
    TextView tvTotalRatingCount;

    @BindView(R.id.tv_tags)
    TextView tvTags;

    @BindView(R.id.tv_new_restaurant)
    TextView tvNewRestaurant;

    private RestaurantVO mRestaurant;

    public RestaurantViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(RestaurantVO data) {
        this.mRestaurant = data;

        if (data.isAd()){
            ivAds.setVisibility(View.VISIBLE);
        }else {
            ivAds.setVisibility(View.INVISIBLE);
        }

        Glide.with(ivRestaurant.getContext())
                .load(data.getImage())
                .centerCrop()
                .crossFade()
                .placeholder(R.drawable.store)
                .error(R.drawable.store)
                .into(ivRestaurant);

        if (data.getAddrShort() != null){
            tvRestaurantTitle.setText(data.getTitle() + " (" + data.getAddrShort() + ")");
        }else {
            tvRestaurantTitle.setText(data.getTitle());
        }

        if (data.isNew()) {
            tvNewRestaurant.setText("New!");
        }else {
            tvNewRestaurant.setText("");
        }

        tvDeliverTime.setText(data.getLeadTimeinMin()+" min.");

        rbRestaurantRating.setRating(data.getAverageRatingValue());

        tvTotalRatingCount.setText("("+data.getTotalRatingCount()+")");

        tvTags.setText(getTags(data.getTags()));

    }

    public String getTags(String[] tags){

        if (tags.length > 0) {
            StringBuilder tagBuilder = new StringBuilder();

            for (String tag : tags) {
                tagBuilder.append(tag.replace("'", "")).append(", ");
            }

            tagBuilder.deleteCharAt(tagBuilder.length() - 2);

            return tagBuilder.toString();
        } else {
            return "";
        }

    }

}
