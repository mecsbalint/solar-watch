package com.mecsbalint.solarwatch.service;

import com.mecsbalint.solarwatch.controller.dto.CityDto;
import com.mecsbalint.solarwatch.exceptions.SettlementAlreadyExistException;
import com.mecsbalint.solarwatch.exceptions.SettlementNotFoundException;
import com.mecsbalint.solarwatch.model.City;
import com.mecsbalint.solarwatch.model.SunsetSunrise;
import com.mecsbalint.solarwatch.repository.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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

    @Test
    public void addCity_cityNotExistYet_noExceptionThrownAndCitySaved() {
        when(cityRepositoryMock.findCityByName(anyString())).thenReturn(null);

        assertDoesNotThrow(() -> cityService.addCity(cityDto));

        ArgumentCaptor<City> cityCaptor = ArgumentCaptor.forClass(City.class);
        verify(cityRepositoryMock, times(1)).save(cityCaptor.capture());

        City savedCity = cityCaptor.getValue();
        assertEquals(cityDto.name(), savedCity.getName());
        assertEquals(cityDto.lat(), savedCity.getLat());
        assertEquals(cityDto.lon(), savedCity.getLon());
        assertEquals(cityDto.country(), savedCity.getCountry());
        assertEquals(cityDto.state(), savedCity.getState());
        assertEquals(cityDto.sunsetSunrises().stream().map(sunsetSunriseDto -> new SunsetSunrise()).toList(), savedCity.getSunsetSunrises());
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

        cityService.updateCity(cityDto);

        ArgumentCaptor<City> cityCaptor = ArgumentCaptor.forClass(City.class);
        verify(cityRepositoryMock, times(1)).save(cityCaptor.capture());

        City updatedCity = cityCaptor.getValue();

        assertEquals(cityDto, new CityDto(updatedCity));

    }

    @Test
    public void updateCity_cityNotExist_throwSettlementNotFoundException() {
        when(cityRepositoryMock.findCityByName(anyString())).thenReturn(null);

        assertThrows(SettlementNotFoundException.class, () -> cityService.updateCity(cityDto));
    }

    @Test
    public void deleteCity_cityExist_callDeleteByNameMethodWithCityName() {
        City city = new City();
        city.setSunsetSunrises(List.of());
        String cityName = "city name";
        when(cityRepositoryMock.findCityByName(anyString())).thenReturn(city);

        cityService.deleteCity(cityName);

        verify(cityRepositoryMock, times(1)).deleteByName(cityName);
    }

    @Test
    public void deleteCity_cityNotExist_throwSettlementNotFoundException() {
        when(cityRepositoryMock.findCityByName(anyString())).thenReturn(null);

        assertThrows(SettlementNotFoundException.class, () -> cityService.deleteCity("city name"));
    }
}