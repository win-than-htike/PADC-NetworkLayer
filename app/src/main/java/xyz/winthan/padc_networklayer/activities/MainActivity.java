package xyz.winthan.padc_networklayer.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.winthan.padc_networklayer.R;
import xyz.winthan.padc_networklayer.adapters.RestaurantRvAdapter;
import xyz.winthan.padc_networklayer.data.network.RetrofitDataAgent;
import xyz.winthan.padc_networklayer.events.DataEvent;

public class MainActivity extends BaseActivity {

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

        RetrofitDataAgent.getInstance().loadRestaurant();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void DataLoadedEvent(DataEvent.RestaurantDataLoadedEvent event){

        mAdapter.setNewData(event.getRestaurantVOList());
        loading.setVisibility(View.GONE);

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
}
