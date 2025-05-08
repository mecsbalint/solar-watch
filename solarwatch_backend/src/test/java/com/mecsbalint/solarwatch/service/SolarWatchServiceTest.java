package com.mecsbalint.solarwatch.service;

import com.mecsbalint.solarwatch.exceptions.SettlementNotFoundException;
import com.mecsbalint.solarwatch.model.City;
import com.mecsbalint.solarwatch.controller.dto.SunsetSunriseFromApiCallDto;
import com.mecsbalint.solarwatch.controller.dto.SunsetSunriseResultsDto;
import com.mecsbalint.solarwatch.repository.CityRepository;
import com.mecsbalint.solarwatch.repository.SunsetSunriseRepository;
import com.mecsbalint.solarwatch.utility.Fetcher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SolarWatchServiceTest {

    private SolarWatchService solarWatchService;
    private final Fetcher fetcherMock = mock(Fetcher.class);
    private final CityRepository cityRepository = mock(CityRepository.class);
    private final SunsetSunriseRepository sunsetSunriseRepository = mock(SunsetSunriseRepository.class);

    @AfterEach
    public void resetMocks() {
        reset(fetcherMock);
    }

    @Test
    void getSolarWatchModel_cityNotExist_throwSettlementNotFoundException() {
        when(fetcherMock.fetch(any(), any())).thenReturn(new City[]{});

        solarWatchService = new SolarWatchService(fetcherMock, cityRepository, sunsetSunriseRepository);

        assertThrows(SettlementNotFoundException.class, () -> solarWatchService.getSolarWatchData("non existent city", LocalDate.now()));
    }

    @Test
    void getSolarWatchModel_cityExist_returnSolarWatchModel() {
        when(fetcherMock.fetch(any(), eq(City[].class))).thenReturn(new City[]{new City()});
        when(fetcherMock.fetch(any(), eq(SunsetSunriseFromApiCallDto.class))).thenReturn(new SunsetSunriseFromApiCallDto(new SunsetSunriseResultsDto("5:12:27 AM", "5:12:27 PM")));

        solarWatchService = new SolarWatchService(fetcherMock, cityRepository, sunsetSunriseRepository);

        String expectedSunrise = "5:12:27 AM";
        String actualSunrise = solarWatchService.getSolarWatchData("Budapest", LocalDate.of(2025, 3, 6)).sunRise();

        assertEquals(expectedSunrise, actualSunrise);
    }
}