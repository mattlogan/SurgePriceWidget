package me.mattlogan.surgepricewidget;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import me.mattlogan.surgepricewidget.api.UberApi;
import me.mattlogan.surgepricewidget.api.UberApiBus;
import me.mattlogan.surgepricewidget.api.UberApiHandler;
import me.mattlogan.surgepricewidget.location.CurrentLocationClient;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class SurgePriceWidgetApplication extends Application {

    static final String ENDPOINT = "https://api.uber.com";
    static final String SERVER_TOKEN = "get_your_own_server_token_at_https://developer.uber.com/";

    UberApiHandler uberApiHandler;
    CurrentLocationClient currentLocationClient;

    @Override public void onCreate() {
        Log.d("testing", "application onCreate");
        super.onCreate();

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override public void run() {
                uberApiHandler = new UberApiHandler(buildUberApi(), UberApiBus.getInstance());
            }
        });
    }

    private UberApi buildUberApi() {
        return new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override public void intercept(RequestFacade request) {
                        request.addQueryParam("server_token", SERVER_TOKEN);
                    }
                })
                .build()
                .create(UberApi.class);
    }
}
