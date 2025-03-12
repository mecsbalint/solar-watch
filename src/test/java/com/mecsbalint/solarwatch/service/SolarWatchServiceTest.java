package com.mecsbalint.solarwatch.service;

import com.mecsbalint.solarwatch.exceptions.SettlementNotFoundException;
import com.mecsbalint.solarwatch.model.citydata.CityGeolocation;
import com.mecsbalint.solarwatch.model.sunsetsunrise.SunsetSunrise;
import com.mecsbalint.solarwatch.model.sunsetsunrise.SunsetSunriseResults;
import com.mecsbalint.solarwatch.repository.CityRepository;
import com.mecsbalint.solarwatch.repository.SunsetSunriseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SolarWatchServiceTest {
    private RestTemplate restTemplateMock;
    private SolarWatchService solarWatchService;
    private final CityRepository cityRepository = mock(CityRepository.class);
    private final SunsetSunriseRepository sunsetSunriseRepository = mock(SunsetSunriseRepository.class);

    @Test
    void getSolarWatchModel_cityNotExist_throwSettlementNotFoundException() {
        restTemplateMock = mock(RestTemplate.class);
        when(restTemplateMock.getForObject(any(), any()))
                .thenReturn("[]");
        solarWatchService = new SolarWatchService(restTemplateMock, cityRepository, sunsetSunriseRepository);

        assertThrows(SettlementNotFoundException.class, () -> solarWatchService.getSolarWatchData("non existent city", LocalDate.now()));
    }

    @Test
    void getSolarWatchModel_cityExist_returnSolarWatchModel() {
        restTemplateMock = mock(RestTemplate.class);
        CityGeolocation[] cityGeolocations = {new CityGeolocation(2,3)};
        when(restTemplateMock.getForObject(anyString(), any()))
                .thenReturn(cityGeolocations)
                .thenReturn(new SunsetSunrise(new SunsetSunriseResults("5:12:27 AM", "5:12:27 PM")));
        solarWatchService = new SolarWatchService(restTemplateMock, cityRepository, sunsetSunriseRepository);

        String expectedSunrise = "5:12:27 AM";
        String actualSunrise = solarWatchService.getSolarWatchData("Budapest", LocalDate.of(2025, 3, 6)).sunRise();

        assertEquals(expectedSunrise, actualSunrise);
    }
}