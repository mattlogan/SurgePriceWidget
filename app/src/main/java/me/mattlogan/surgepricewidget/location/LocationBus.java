package me.mattlogan.surgepricewidget.location;

import com.squareup.otto.Bus;

public class LocationBus extends Bus {

    private static LocationBus locationBus;

    public static LocationBus getInstance() {
        if (locationBus == null) {
            locationBus = new LocationBus();
        }
        return locationBus;
    }
}
