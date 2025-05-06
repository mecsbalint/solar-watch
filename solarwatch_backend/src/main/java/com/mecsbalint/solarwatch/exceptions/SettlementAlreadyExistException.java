package com.mecsbalint.solarwatch.exceptions;

public class SettlementAlreadyExistException extends RuntimeException {
    public SettlementAlreadyExistException(String cityName) {
        super(String.format("The %s settlement is already exist in the SolarWatch database", cityName));
    }
}
