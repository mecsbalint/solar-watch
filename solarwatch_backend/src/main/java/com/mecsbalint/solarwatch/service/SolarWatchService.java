package com.mecsbalint.solarwatch.service;

import com.mecsbalint.solarwatch.utility.Fetcher;
import com.mecsbalint.solarwatch.exceptions.SettlementNotFoundException;
import com.mecsbalint.solarwatch.model.City;
import com.mecsbalint.solarwatch.controller.dto.SolarWatchDto;
import com.mecsbalint.solarwatch.model.SunsetSunrise;
import com.mecsbalint.solarwatch.controller.dto.SunsetSunriseFromApiCallDto;
import com.mecsbalint.solarwatch.repository.CityRepository;
import com.mecsbalint.solarwatch.repository.SunsetSunriseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SolarWatchService {
    private static final Logger logger = LoggerFactory.getLogger(SolarWatchService.class);

    @Value("${GEOCODING_KEY}")
    private String API_KEY;

    private final Fetcher fetcher;
    private final CityRepository cityRepository;
    private final SunsetSunriseRepository sunsetSunriseRepository;


    public SolarWatchService(Fetcher fetcher, CityRepository cityRepository, SunsetSunriseRepository sunsetSunriseRepository) {
        this.fetcher = fetcher;
        this.cityRepository = cityRepository;
        this.sunsetSunriseRepository = sunsetSunriseRepository;
    }

    public SolarWatchDto getSolarWatchData(String cityName, LocalDate date) {
        City city = cityRepository.findCityByName(cityName);
        SunsetSunrise sunsetSunrise = sunsetSunriseRepository.findSunsetSunriseByCityAndDate(city, date);
        if (sunsetSunrise != null) {
            return new SolarWatchDto(
                    cityName,
                    date,
                    sunsetSunrise.getSunrise(),
                    sunsetSunrise.getSunset()
            );
        }

        if (city == null) {
            city = getCityFromApi(cityName);
            cityRepository.save(city);
        }
        sunsetSunrise = getSunsetSunriseFromApi(city, date);
        sunsetSunriseRepository.save(sunsetSunrise);

        return new SolarWatchDto(
                cityName,
                date,
                sunsetSunrise.getSunrise(),
                sunsetSunrise.getSunset()
        );
    }

    private SunsetSunrise getSunsetSunriseFromApi(City city, LocalDate date) {
        double lon = city.getLon();
        double lat = city.getLat();
        String url = String.format("https://api.sunrise-sunset.org/json?lat=%f&lng=%f&date=%s", lat, lon, date);

        SunsetSunriseFromApiCallDto response = fetcher.fetch(url, SunsetSunriseFromApiCallDto.class);

        logger.info("Response from Sunset and Sunrise Time API: {}", response);

        SunsetSunrise sunsetSunrise = new SunsetSunrise();
        sunsetSunrise.setCity(city);
        sunsetSunrise.setDate(date);
        sunsetSunrise.setSunrise(response.results().sunrise());
        sunsetSunrise.setSunset(response.results().sunset());

        return sunsetSunrise;
    }

    private City getCityFromApi(String cityName) {
        String url = String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s&appid=%s", cityName, API_KEY);

        try {
            City[] response = fetcher.fetch(url, City[].class);

            logger.info("Response from Geocoding API: {}", response);

            return response[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new SettlementNotFoundException(cityName, "Geocoding API (https://openweathermap.org/api )");
        }
    }
}
