package me.mattlogan.surgepricewidget.api.event;

import me.mattlogan.surgepricewidget.api.model.PriceListWrapper;

public class RetrievePricesSuccessEvent {

    PriceListWrapper priceListWrapper;

    public RetrievePricesSuccessEvent(PriceListWrapper priceListWrapper) {
        this.priceListWrapper = priceListWrapper;
    }

    public PriceListWrapper getPriceListWrapper() {
        return priceListWrapper;
    }
}
