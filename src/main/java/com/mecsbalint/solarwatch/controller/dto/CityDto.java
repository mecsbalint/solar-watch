package com.mecsbalint.solarwatch.controller.dto;

import com.mecsbalint.solarwatch.model.City;

import java.util.List;

public record CityDto(String name, double lon, double lat, String country, String state, List<SunsetSunriseDto> sunsetSunrises) {
    public CityDto(City city) {
        this(
                city.getName(),
                city.getLon(),
                city.getLat(),
                city.getCountry(),
                city.getState(),
                city.getSunsetSunrises().stream().map(SunsetSunriseDto::new).toList()
        );
    }
}
