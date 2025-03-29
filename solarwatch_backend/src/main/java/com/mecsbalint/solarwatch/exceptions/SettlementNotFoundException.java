package com.mecsbalint.solarwatch.exceptions;

public class SettlementNotFoundException extends RuntimeException {
    public SettlementNotFoundException(String cityName) {
        super(String.format("There is no settlement under %s name in the Geocoding API.", cityName));
    }
}
