package com.mecsbalint.solarwatch.service;

import com.mecsbalint.solarwatch.controller.dto.CityDto;
import com.mecsbalint.solarwatch.exceptions.SettlementAlreadyExistException;
import com.mecsbalint.solarwatch.exceptions.SettlementNotFoundException;
import com.mecsbalint.solarwatch.model.City;
import com.mecsbalint.solarwatch.repository.CityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    @Mock
    private CityRepository cityRepositoryMock;

    @Mock
    private SunsetSunriseService sunsetSunriseServiceMock;

    private CityService cityService;

    private final CityDto cityDto = new CityDto("name", 1.0, 1.0, "country", "state", new ArrayList<>());

    @BeforeEach
    public void setUp() {
        cityService = new CityService(cityRepositoryMock, sunsetSunriseServiceMock);
    }

    @AfterEach
    public void resetMocks() {
        reset(cityRepositoryMock);
        reset(sunsetSunriseServiceMock);
    }

    @Test
    public void addCity_cityNotExistYet_noExceptionThrown() {
        when(cityRepositoryMock.findCityByName(anyString())).thenReturn(null);
        when(sunsetSunriseServiceMock.generateSunsetSunriseFromSunsetSunriseDto(any(), any())).thenReturn(null);

        assertDoesNotThrow(() -> cityService.addCity(cityDto));
    }

    @Test
    public void addCity_cityAlreadyExist_throwSettlementAlreadyExistException() {
        when(cityRepositoryMock.findCityByName(anyString())).thenReturn(new City());

        assertThrows(SettlementAlreadyExistException.class, () -> cityService.addCity(cityDto));
    }

    @Test
    public void updateCity_cityExist_returnCityDto() {
        when(cityRepositoryMock.findCityByName(anyString())).thenReturn(new City());
        City city = new City();
        city.setSunsetSunrises(List.of());
        when(cityRepositoryMock.save(any())).thenReturn(city);

        CityDto actualResult = cityService.updateCity(cityDto);

        assertEquals(CityDto.class, actualResult.getClass());
    }

    @Test
    public void updateCity_cityNotExist_throwSettlementNotFoundException() {
        when(cityRepositoryMock.findCityByName(anyString())).thenReturn(null);

        assertThrows(SettlementNotFoundException.class, () -> cityService.updateCity(cityDto));
    }

    @Test
    public void deleteCity_cityExist_returnCityDto() {
        City city = new City();
        city.setSunsetSunrises(List.of());
        when(cityRepositoryMock.findCityByName(anyString())).thenReturn(city);

        CityDto actualResult = cityService.deleteCity("city name");

        assertEquals(CityDto.class, actualResult.getClass());
    }

    @Test
    public void deleteCity_cityNotExist_throwSettlementNotFoundException() {
        when(cityRepositoryMock.findCityByName(anyString())).thenReturn(null);

        assertThrows(SettlementNotFoundException.class, () -> cityService.deleteCity("city name"));
    }
}