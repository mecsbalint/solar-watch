package com.mecsbalint.solarwatch.exceptions;

public class SettlementNotFoundException extends RuntimeException {
    public SettlementNotFoundException(String cityName, String dataBaseName) {
        super(String.format("There is no settlement under %s name in the %s", cityName, dataBaseName));
    }
}
