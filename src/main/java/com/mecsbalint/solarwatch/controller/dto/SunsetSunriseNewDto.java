package com.mecsbalint.solarwatch.controller.dto;

import java.time.LocalDate;

public record SunsetSunriseNewDto(long cityId, LocalDate date, String sunrise, String sunset) {
}
