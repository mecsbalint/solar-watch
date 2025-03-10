package com.mecsbalint.solarwatch.model.sunsetsunrise;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SunsetSunrise(SunsetSunriseResults results) {
}
