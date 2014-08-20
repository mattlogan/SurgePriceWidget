package me.mattlogan.surgepricewidget;

import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.List;

import me.mattlogan.surgepricewidget.api.UberApiBus;
import me.mattlogan.surgepricewidget.api.event.RetrievePricesRequestedEvent;
import me.mattlogan.surgepricewidget.api.model.Price;
import me.mattlogan.surgepricewidget.api.event.RetrievePricesFailedEvent;
import me.mattlogan.surgepricewidget.api.event.RetrievePricesSuccessEvent;
import me.mattlogan.surgepricewidget.location.CurrentLocationClient;
import me.mattlogan.surgepricewidget.location.LocationBus;
import me.mattlogan.surgepricewidget.location.event.LocationClientConnectedEvent;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    UberApiBus uberApiBus = UberApiBus.getInstance();
    LocationBus locationBus = LocationBus.getInstance();

    Button retrievePricesButton;
    ListView pricesListView;
    PriceListAdapter priceListAdapter;

    Location lastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrievePricesButton = (Button) findViewById(R.id.get_prices_button);
        retrievePricesButton.setOnClickListener(this);

        priceListAdapter = new PriceListAdapter(this);

        pricesListView = (ListView) findViewById(R.id.prices_list);
        pricesListView.setAdapter(priceListAdapter);
    }

    @Override public void onResume() {
        super.onResume();
        uberApiBus.register(this);
        locationBus.register(this);
        CurrentLocationClient.getClient(this).connect();
    }

    @Override public void onPause() {
        super.onPause();
        uberApiBus.unregister(this);
        locationBus.unregister(this);
        CurrentLocationClient.getClient(this).disconnect();
    }

    @Subscribe public void onRetrievePricesSuccess(RetrievePricesSuccessEvent event) {
        List<Price> priceList = event.getPriceListWrapper().getPrices();
        priceListAdapter.setPriceList(priceList);
        priceListAdapter.notifyDataSetChanged();
    }

    @Subscribe public void onRetrievePricesFailed(RetrievePricesFailedEvent event) {
        Toast.makeText(this, "Failed to get prices", Toast.LENGTH_SHORT).show();
    }

    @Override public void onClick(View view) {
        if (view == retrievePricesButton) {
            if (lastLocation != null) {
                uberApiBus.post(new RetrievePricesRequestedEvent(
                        (float) lastLocation.getLatitude(), (float) lastLocation.getLongitude(),
                        (float) lastLocation.getLatitude(), (float) lastLocation.getLongitude()));
            } else {
                Toast.makeText(this, "Failed to get your location", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Subscribe public void onLocationClientConnected(LocationClientConnectedEvent event) {
        lastLocation = CurrentLocationClient.getClient(this).getLastLocation();
    }
}
