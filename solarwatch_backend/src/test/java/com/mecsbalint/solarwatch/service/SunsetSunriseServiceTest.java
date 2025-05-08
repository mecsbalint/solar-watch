package com.mecsbalint.solarwatch.service;

import com.mecsbalint.solarwatch.controller.dto.CityDto;
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
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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

    @Test void addSunsetSunrise_cityExistSunsetSunriseNotExist_throwNoExceptionAndSunsetSunriseSaved() {
        City city = new City();
        city.setId(sunsetSunriseNewDto.cityId());
        when(cityRepositoryMock.findCityById(anyLong())).thenReturn(city);
        when(sunsetSunriseRepositoryMock.findSunsetSunriseByCityAndDate(any(), any())).thenReturn(null);

        assertDoesNotThrow(() -> sunsetSunriseService.addSunsetSunrise(sunsetSunriseNewDto));

        ArgumentCaptor<SunsetSunrise> sunsetSunriseCaptor = ArgumentCaptor.forClass(SunsetSunrise.class);
        verify(sunsetSunriseRepositoryMock, times(1)).save(sunsetSunriseCaptor.capture());

        SunsetSunrise savedSunsetSunrise = sunsetSunriseCaptor.getValue();
        assertEquals(sunsetSunriseNewDto.cityId(), savedSunsetSunrise.getCity().getId());
        assertEquals(sunsetSunriseNewDto.date(), savedSunsetSunrise.getDate());
        assertEquals(sunsetSunriseNewDto.sunrise(), savedSunsetSunrise.getSunrise());
        assertEquals(sunsetSunriseNewDto.sunset(), savedSunsetSunrise.getSunset());
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
        City city = new City();
        city.setId(sunsetSunriseNewDto.cityId());
        when(cityRepositoryMock.findCityById(anyLong())).thenReturn(city);
        when(sunsetSunriseRepositoryMock.existsById(anyLong())).thenReturn(true);

        sunsetSunriseService.updateSunsetSunrise(sunsetSunriseDto);

        ArgumentCaptor<SunsetSunrise> sunsetSunriseCaptor = ArgumentCaptor.forClass(SunsetSunrise.class);
        verify(sunsetSunriseRepositoryMock, times(1)).save(sunsetSunriseCaptor.capture());

        SunsetSunrise updatedSunsetSunrise = sunsetSunriseCaptor.getValue();

        assertEquals(sunsetSunriseDto, new SunsetSunriseDto(updatedSunsetSunrise));
    }

    @Test
    public void deleteSunsetSunrise_sunsetSunriseNotExist_throwSunriseSunsetNotFoundException() {
        when(sunsetSunriseRepositoryMock.findSunsetSunriseById(anyLong())).thenReturn(null);

        assertThrows(SunriseSunsetNotFoundException.class, () -> sunsetSunriseService.deleteSunsetSunrise(1));
    }

    @Test
    public void deleteSunsetSunrise_sunsetSunriseExist_returnSunsetSunriseDto() {
        long id = 1L;
        SunsetSunrise sunsetSunrise = new SunsetSunrise();
        sunsetSunrise.setCity(new City());
        when(sunsetSunriseRepositoryMock.findSunsetSunriseById(anyLong())).thenReturn(sunsetSunrise);

        sunsetSunriseService.deleteSunsetSunrise(id);

        verify(sunsetSunriseRepositoryMock, times(1)).deleteById(id);
    }
}