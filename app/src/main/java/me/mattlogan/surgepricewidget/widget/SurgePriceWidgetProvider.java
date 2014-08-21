package me.mattlogan.surgepricewidget.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.google.android.gms.location.LocationClient;
import com.squareup.otto.Subscribe;

import java.util.List;

import me.mattlogan.surgepricewidget.R;
import me.mattlogan.surgepricewidget.api.UberApiBus;
import me.mattlogan.surgepricewidget.api.event.RetrievePricesFailedEvent;
import me.mattlogan.surgepricewidget.api.event.RetrievePricesRequestedEvent;
import me.mattlogan.surgepricewidget.api.event.RetrievePricesSuccessEvent;
import me.mattlogan.surgepricewidget.api.model.Price;
import me.mattlogan.surgepricewidget.location.CurrentLocationClient;
import me.mattlogan.surgepricewidget.location.LocationBus;
import me.mattlogan.surgepricewidget.location.event.LocationClientConnectedEvent;

public class SurgePriceWidgetProvider extends AppWidgetProvider {

    Context context;

    UberApiBus uberApiBus = UberApiBus.getInstance();
    LocationBus locationBus = LocationBus.getInstance();

    LocationClient locationClient;

    AppWidgetManager appWidgetManager;
    int[] appWidgetIds;

    int[] nameTextViews = new int[]{
            R.id.uber_name_1,
            R.id.uber_name_2,
            R.id.uber_name_3,
            R.id.uber_name_4,
            R.id.uber_name_5
    };

    int[] surgePriceTextViews = new int[]{
            R.id.surge_price_1,
            R.id.surge_price_2,
            R.id.surge_price_3,
            R.id.surge_price_4,
            R.id.surge_price_5
    };

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        this.context = context;
        this.appWidgetManager = appWidgetManager;
        this.appWidgetIds = appWidgetIds;

        uberApiBus.register(this);
        locationBus.register(this);

        if (locationClient == null) {
            locationClient = CurrentLocationClient.getClient(context);
        }
        locationClient.connect();
    }

    @Subscribe public void onLocationClientConnected(LocationClientConnectedEvent event) {
        Location location = locationClient.getLastLocation();
        uberApiBus.post(new RetrievePricesRequestedEvent(
                (float) location.getLatitude(), (float) location.getLongitude(),
                (float) location.getLatitude(), (float) location.getLongitude()));
    }

    @Subscribe public void onRetrievePricesSuccess(RetrievePricesSuccessEvent event) {
        updateViews(event.getPriceListWrapper().getPrices());
        cleanup();
    }

    @Subscribe public void onRetrievePricesFailed(RetrievePricesFailedEvent event) {
        cleanup();
    }

    void updateViews(List<Price> priceList) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

            for (int i = 0; i < nameTextViews.length; i++) {
                if (i > priceList.size() - 1) {
                    views.setViewVisibility(nameTextViews[i], View.GONE);
                    views.setViewVisibility(surgePriceTextViews[i], View.GONE);
                } else {
                    views.setViewVisibility(nameTextViews[i], View.VISIBLE);
                    views.setViewVisibility(surgePriceTextViews[i], View.VISIBLE);

                    String nameText = chopNameText(priceList.get(i).getLocalizedDisplayName());
                    views.setTextViewText(nameTextViews[i], nameText);
                    views.setTextViewText(surgePriceTextViews[i],
                            "" + priceList.get(i).getSurgeMultiplier());
                }
            }

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    void cleanup() {
        uberApiBus.unregister(this);
        locationBus.unregister(this);
        locationClient.disconnect();
    }

    String chopNameText(String string) {
        return string.toUpperCase().replace("UBER", "");
    }
}
