package com.mecsbalint.solarwatch.exceptions;

public class SunriseSunsetNotFoundException extends RuntimeException {
    public SunriseSunsetNotFoundException() {
        super("There is no sunrise/sunset information in the SolarWatch database");
    }
}
