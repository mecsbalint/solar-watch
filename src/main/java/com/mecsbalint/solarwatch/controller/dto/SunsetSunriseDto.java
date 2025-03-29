package com.mecsbalint.solarwatch.controller.dto;

import com.mecsbalint.solarwatch.model.SunsetSunrise;

import java.time.LocalDate;

public record SunsetSunriseDto(long id, long cityId, LocalDate date, String sunrise, String sunset) {
    public SunsetSunriseDto(SunsetSunrise sunsetSunrise) {
        this(
                sunsetSunrise.getId(),
                sunsetSunrise.getCity().getId(),
                sunsetSunrise.getDate(),
                sunsetSunrise.getSunrise(),
                sunsetSunrise.getSunset());
    }
}
