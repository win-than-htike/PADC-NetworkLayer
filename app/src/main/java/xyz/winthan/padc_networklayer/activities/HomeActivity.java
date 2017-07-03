package xyz.winthan.padc_networklayer.activities;

import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.winthan.padc_networklayer.R;
import xyz.winthan.padc_networklayer.adapters.RestaurantRvAdapter;
import xyz.winthan.padc_networklayer.data.persistence.RestaurantContract;
import xyz.winthan.padc_networklayer.data.vos.RestaurantVO;
import xyz.winthan.padc_networklayer.mvp.presenters.BasePresenter;
import xyz.winthan.padc_networklayer.mvp.presenters.RestaurantListPresenter;
import xyz.winthan.padc_networklayer.mvp.views.RestaurantListView;
import xyz.winthan.padc_networklayer.utils.RestaurantConstants;

public class HomeActivity extends BaseActivity implements RestaurantListView, LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.rv_restaurants)
    RecyclerView rvRestaurants;

    @BindView(R.id.pb_loading)
    ProgressBar loading;

    private RestaurantRvAdapter mAdapter;

    private RestaurantListPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this, this);

        mPresenter = new RestaurantListPresenter(this, this);
        mPresenter.onCreate();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAdapter = new RestaurantRvAdapter(this);
        rvRestaurants.setHasFixedSize(true);
        rvRestaurants.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvRestaurants.setItemAnimator(new DefaultItemAnimator());
        rvRestaurants.setAdapter(mAdapter);

        loading.setVisibility(View.VISIBLE);

        getSupportLoaderManager().initLoader(RestaurantConstants.RESTAURANT_LIST_LOADER, null, this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestory();
    }

    @Override
    public void displayRestaurantList(List<RestaurantVO> restaurantVOList) {

        loading.setVisibility(View.GONE);
        mAdapter.setNewData(restaurantVOList);

    }

    @Override
    public void displayFailToLoadError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isEmptyRestaurantList() {
        return false;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                RestaurantContract.RestaurantEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        List<RestaurantVO> restaurantVOList = new ArrayList<>();

        Log.i("data count => ", data.getCount()+"");
        if (data != null && data.moveToFirst()){


            do {

                RestaurantVO restaurantVO = RestaurantVO.parseFromCursor(data);
                restaurantVO.setTags(RestaurantVO.loadRestaurantTagByTitle(this, restaurantVO.getImage()));
                restaurantVOList.add(restaurantVO);

            }while (data.moveToNext());

            mPresenter.loadRestaurantFromDb(restaurantVOList);

        }else {
            loading.setVisibility(View.VISIBLE);
            mPresenter.loadRestaurantData();
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
