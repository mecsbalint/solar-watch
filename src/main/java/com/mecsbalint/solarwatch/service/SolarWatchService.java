package com.mecsbalint.solarwatch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mecsbalint.solarwatch.exceptions.SettlementNotFoundException;
import com.mecsbalint.solarwatch.model.citydata.CityGeolocation;
import com.mecsbalint.solarwatch.model.SolarWatchModel;
import com.mecsbalint.solarwatch.model.sunsetsunrise.SunsetSunrise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Service
public class SolarWatchService {

    @Value("${GEOCODING_KEY}")
    private String API_KEY;

    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(SolarWatchService.class);


    public SolarWatchService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public SolarWatchModel getSolarWatchModel(String city, LocalDate date) {
        CityGeolocation cityGeolocation = getCityGeolocation(city);

        double lon = cityGeolocation.lon();
        double lat = cityGeolocation.lat();
        String url = String.format("https://api.sunrise-sunset.org/json?lat=%f&lng=%f&date=%s", lat, lon, date);

        SunsetSunrise response = restTemplate.getForObject(url, SunsetSunrise.class);

        logger.info("Response from Sunset and Sunrise Time API: {}", response);

        return new SolarWatchModel(
                city,
                date,
                response.results().sunrise(),
                response.results().sunset()
        );
    }

    private CityGeolocation getCityGeolocation(String cityName) {
        String url = String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s&appid=%s", cityName, API_KEY);

        CityGeolocation[] response = restTemplate.getForObject(url, CityGeolocation[].class);

        logger.info("Response from Geocoding API: {}", response);

        try {
            return response[0];
        } catch (NullPointerException e) {
            throw new SettlementNotFoundException(cityName);
        }
    }
}
