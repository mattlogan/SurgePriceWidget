package me.mattlogan.surgepricewidget.api.model;

import java.util.List;

public class PriceListWrapper {

    List<Price> prices;

    public PriceListWrapper(List<Price> prices) {
        this.prices = prices;
    }

    public List<Price> getPrices() {
        return prices;
    }
}
