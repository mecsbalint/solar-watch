package com.mecsbalint.solarwatch.controller.dto;

import java.time.LocalDate;

public record CityNameAndDateDto(String cityName, LocalDate date) {
}
