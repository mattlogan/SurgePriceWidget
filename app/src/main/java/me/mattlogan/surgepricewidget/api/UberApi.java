package me.mattlogan.surgepricewidget.api;

import java.util.Map;

import me.mattlogan.surgepricewidget.api.model.PriceListWrapper;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.QueryMap;

public interface UberApi {
    @GET("/v1/estimates/price") void getPriceEstimates(@QueryMap Map<String, Float> locations,
                                                       Callback<PriceListWrapper> callback);
}
