package me.mattlogan.surgepricewidget.api.event;

public class RetrievePricesRequestedEvent {

    public float startLatitude;
    public float startLongitude;
    public float endLatitude;
    public float endLongitude;

    public RetrievePricesRequestedEvent(float startLatitude, float startLongitude,
                                        float endLatitude, float endLongitude) {

        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.endLatitude = endLatitude;
        this.endLongitude = endLongitude;
    }
}
