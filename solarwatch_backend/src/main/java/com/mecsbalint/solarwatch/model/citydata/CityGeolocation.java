package com.mecsbalint.solarwatch.model.citydata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CityGeolocation(double lon, double lat) {
}
