package com.mecsbalint.solarwatch.service;

import com.mecsbalint.solarwatch.controller.dto.SunsetSunriseDto;
import com.mecsbalint.solarwatch.controller.dto.SunsetSunriseNewDto;
import com.mecsbalint.solarwatch.exceptions.SettlementNotFoundException;
import com.mecsbalint.solarwatch.exceptions.SunriseSunsetAlreadyExistException;
import com.mecsbalint.solarwatch.exceptions.SunriseSunsetNotFoundException;
import com.mecsbalint.solarwatch.model.City;
import com.mecsbalint.solarwatch.model.SunsetSunrise;
import com.mecsbalint.solarwatch.repository.CityRepository;
import com.mecsbalint.solarwatch.repository.SunsetSunriseRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SunsetSunriseServiceTest {

    @Mock
    private SunsetSunriseRepository sunsetSunriseRepositoryMock;

    @Mock
    private CityRepository cityRepositoryMock;

    private SunsetSunriseService sunsetSunriseService;

    private final SunsetSunriseDto sunsetSunriseDto = new SunsetSunriseDto(1L, 1L, LocalDate.now(), "5:12:27 AM", "5:12:27 PM");
    private final SunsetSunriseNewDto sunsetSunriseNewDto = new SunsetSunriseNewDto(1L,LocalDate.now(), "5:12:27 AM", "5:12:27 PM");

    @BeforeEach
    public void setUp() {
        sunsetSunriseService = new SunsetSunriseService(sunsetSunriseRepositoryMock, cityRepositoryMock);
    }

    @AfterEach
    public void resetMocks() {
        reset(sunsetSunriseRepositoryMock);
        reset(cityRepositoryMock);
    }

    @Test
    public void addSunsetSunrise_cityNotExist_throwSettlementNotFoundException() {
        when(cityRepositoryMock.findCityById(anyLong())).thenReturn(null);

        assertThrows(SettlementNotFoundException.class, () -> sunsetSunriseService.addSunsetSunrise(sunsetSunriseNewDto));
    }

    @Test
    public void addSunsetSunrise_cityAndSunsetSunriseExist_throwSunriseSunsetAlreadyExistException() {
        when(cityRepositoryMock.findCityById(anyLong())).thenReturn(new City());
        when(sunsetSunriseRepositoryMock.findSunsetSunriseByCityAndDate(any(), any())).thenReturn(new SunsetSunrise());

        assertThrows(SunriseSunsetAlreadyExistException.class, () -> sunsetSunriseService.addSunsetSunrise(sunsetSunriseNewDto));
    }

    @Test void addSunsetSunrise_cityExistSunsetSunriseNotExist_throwNoException() {
        when(cityRepositoryMock.findCityById(anyLong())).thenReturn(new City());
        when(sunsetSunriseRepositoryMock.findSunsetSunriseByCityAndDate(any(), any())).thenReturn(null);

        assertDoesNotThrow(() -> sunsetSunriseService.addSunsetSunrise(sunsetSunriseNewDto));
    }

    @Test
    public void updateSunsetSunrise_cityNotExist_throwSettlementNotFoundException() {
        when(cityRepositoryMock.findCityById(anyLong())).thenReturn(null);

        assertThrows(SettlementNotFoundException.class, () -> sunsetSunriseService.updateSunsetSunrise(sunsetSunriseDto));
    }

    @Test
    public void updateSunsetSunrise_cityExistSunsetSunriseNotExist_throwSunriseSunsetNotFoundException() {
        when(cityRepositoryMock.findCityById(anyLong())).thenReturn(new City());
        when(sunsetSunriseRepositoryMock.existsById(anyLong())).thenReturn(false);

        assertThrows(SunriseSunsetNotFoundException.class, () -> sunsetSunriseService.updateSunsetSunrise(sunsetSunriseDto));
    }

    @Test
    public void updateSunsetSunrise_cityAndSunsetSunriseExist_returnSunsetSunriseDto() {
        when(cityRepositoryMock.findCityById(anyLong())).thenReturn(new City());
        when(sunsetSunriseRepositoryMock.existsById(anyLong())).thenReturn(true);

        SunsetSunriseDto actualResult = sunsetSunriseService.updateSunsetSunrise(sunsetSunriseDto);

        assertEquals(SunsetSunriseDto.class, actualResult.getClass());
    }

    @Test
    public void deleteSunsetSunrise_sunsetSunriseNotExist_throwSunriseSunsetNotFoundException() {
        when(sunsetSunriseRepositoryMock.findSunsetSunriseById(anyLong())).thenReturn(null);

        assertThrows(SunriseSunsetNotFoundException.class, () -> sunsetSunriseService.deleteSunsetSunrise(1));
    }

    @Test
    public void deleteSunsetSunrise_sunsetSunriseExist_returnSunsetSunriseDto() {
        SunsetSunrise sunsetSunrise = new SunsetSunrise();
        sunsetSunrise.setCity(new City());
        when(sunsetSunriseRepositoryMock.findSunsetSunriseById(anyLong())).thenReturn(sunsetSunrise);

        SunsetSunriseDto actualResult = sunsetSunriseService.deleteSunsetSunrise(1);

        assertEquals(SunsetSunriseDto.class, actualResult.getClass());
    }
}