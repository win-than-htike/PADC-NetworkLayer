package xyz.winthan.padc_networklayer.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.winthan.padc_networklayer.R;
import xyz.winthan.padc_networklayer.adapters.RestaurantRvAdapter;
import xyz.winthan.padc_networklayer.data.persistence.RestaurantContract;
import xyz.winthan.padc_networklayer.data.vos.RestaurantVO;
import xyz.winthan.padc_networklayer.events.DataEvent;
import xyz.winthan.padc_networklayer.utils.RestaurantConstants;

public class MainActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    @BindView(R.id.rv_restaurants)
    RecyclerView rvRestaurants;

    @BindView(R.id.pb_loading)
    ProgressBar loading;

    private RestaurantRvAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this, this);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ErrorLoadedEvent(DataEvent.ErrorLoadedEvent event){
        Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

            loading.setVisibility(View.GONE);

            do {

                RestaurantVO restaurantVO = RestaurantVO.parseFromCursor(data);
                restaurantVO.setTags(RestaurantVO.loadRestaurantTagByTitle(this, restaurantVO.getImage()));
                restaurantVOList.add(restaurantVO);

            }while (data.moveToNext());

            mAdapter.setNewData(restaurantVOList);

        }else {
            loading.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
