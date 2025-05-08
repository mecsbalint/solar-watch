package com.mecsbalint.solarwatch.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SunsetSunriseResultsDto(String sunrise, String sunset) {
}
