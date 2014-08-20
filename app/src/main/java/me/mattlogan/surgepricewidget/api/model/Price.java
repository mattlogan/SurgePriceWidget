package me.mattlogan.surgepricewidget.api.model;

public class Price {

    String currency_code;
    String localized_display_name;
    float surge_multiplier;

    public Price(String currency_code, String localized_display_name, float surge_multiplier) {
        this.currency_code = currency_code;
        this.localized_display_name = localized_display_name;
        this.surge_multiplier = surge_multiplier;
    }

    public String getCurrencyCode() {
        return currency_code;
    }

    public String getLocalizedDisplayName() {
        return localized_display_name;
    }

    public float getSurgeMultiplier() {
        return surge_multiplier;
    }
}
