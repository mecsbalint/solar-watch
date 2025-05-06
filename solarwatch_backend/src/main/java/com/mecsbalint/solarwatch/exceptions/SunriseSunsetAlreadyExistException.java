package com.mecsbalint.solarwatch.exceptions;

import java.time.LocalDate;

public class SunriseSunsetAlreadyExistException extends RuntimeException {
    public SunriseSunsetAlreadyExistException(String cityName, LocalDate date) {
        super(String.format("The %s settlement has already sunrise and sunset data for %s", cityName, date.toString()));
    }
}
