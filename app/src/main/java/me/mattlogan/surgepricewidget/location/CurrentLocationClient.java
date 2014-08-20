package me.mattlogan.surgepricewidget.location;

import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

import me.mattlogan.surgepricewidget.location.event.LocationClientConnectedEvent;

public class CurrentLocationClient {

    static LocationClient locationClient;

    public static LocationClient getClient(Context context) {
        if (locationClient == null) {
            locationClient = new LocationClient(
                    context.getApplicationContext(), callbacks, failureListener);
        }
        return locationClient;
    }

    static GooglePlayServicesClient.ConnectionCallbacks callbacks =
            new GooglePlayServicesClient.ConnectionCallbacks() {
                @Override public void onConnected(Bundle bundle) {
                    LocationBus.getInstance().post(new LocationClientConnectedEvent());
                }

                @Override public void onDisconnected() {

                }
            };

    static GooglePlayServicesClient.OnConnectionFailedListener failureListener =
            new GooglePlayServicesClient.OnConnectionFailedListener() {
                @Override public void onConnectionFailed(ConnectionResult connectionResult) {

                }
            };
}
