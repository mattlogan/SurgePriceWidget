package me.mattlogan.surgepricewidget;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import me.mattlogan.surgepricewidget.api.UberApi;
import me.mattlogan.surgepricewidget.api.UberApiBus;
import me.mattlogan.surgepricewidget.api.UberApiHandler;
import me.mattlogan.surgepricewidget.location.LocationBus;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class SurgePriceWidgetApplication extends Application {

    static final String ENDPOINT = "https://api.uber.com";

    UberApiHandler uberApiHandler;

    @Override public void onCreate() {
        super.onCreate();

        final String serverToken = getString(R.string.server_token);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override public void run() {
                uberApiHandler = new UberApiHandler(buildUberApi(serverToken),
                        UberApiBus.getInstance());
            }
        });
    }

    private UberApi buildUberApi(final String serverToken) {
        return new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override public void intercept(RequestFacade request) {
                        request.addQueryParam("server_token", serverToken);
                    }
                })
                .build()
                .create(UberApi.class);
    }


}
