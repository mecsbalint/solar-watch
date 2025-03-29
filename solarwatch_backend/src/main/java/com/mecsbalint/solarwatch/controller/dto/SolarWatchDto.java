package com.mecsbalint.solarwatch.controller.dto;

import java.time.LocalDate;

public record SolarWatchDto(String cityName, LocalDate date, String sunRise, String sunSet) {
}
