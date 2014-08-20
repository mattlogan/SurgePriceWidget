package me.mattlogan.surgepricewidget.api;

import com.squareup.otto.Bus;

public class UberApiBus extends Bus {

    private static UberApiBus uberApiBus;

    public static UberApiBus getInstance() {
        if (uberApiBus == null) {
            uberApiBus = new UberApiBus();
        }
        return uberApiBus;
    }
}
