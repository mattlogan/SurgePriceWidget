package me.mattlogan.surgepricewidget.api;

import android.util.Log;

import com.squareup.otto.Subscribe;

import java.util.HashMap;
import java.util.Map;

import me.mattlogan.surgepricewidget.api.event.RetrievePricesFailedEvent;
import me.mattlogan.surgepricewidget.api.event.RetrievePricesRequestedEvent;
import me.mattlogan.surgepricewidget.api.event.RetrievePricesSuccessEvent;
import me.mattlogan.surgepricewidget.api.model.PriceListWrapper;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UberApiHandler {

    UberApi uberApi;
    UberApiBus uberApiBus;

    public UberApiHandler(UberApi uberApi, UberApiBus uberApiBus) {
        this.uberApi = uberApi;
        this.uberApiBus = uberApiBus;
        this.uberApiBus.register(this);
    }

    @Subscribe public void onRetrievePricesRequested(RetrievePricesRequestedEvent event) {
        Map<String, Float> locations = new HashMap<String, Float>();
        locations.put("start_latitude", event.startLatitude);
        locations.put("start_longitude", event.startLongitude);
        locations.put("end_latitude", event.endLatitude);
        locations.put("end_longitude", event.endLongitude);
        uberApi.getPriceEstimates(locations, new Callback<PriceListWrapper>() {
                    @Override public void success(PriceListWrapper priceListWrapper, Response response) {
                        uberApiBus.post(new RetrievePricesSuccessEvent(priceListWrapper));
                    }

                    @Override public void failure(RetrofitError error) {
                        uberApiBus.post(new RetrievePricesFailedEvent());
                    }
                });
    }
}
