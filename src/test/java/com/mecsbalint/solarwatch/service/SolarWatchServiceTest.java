package com.mecsbalint.solarwatch.service;

import com.mecsbalint.solarwatch.exceptions.SettlementNotFoundException;
import com.mecsbalint.solarwatch.model.City;
import com.mecsbalint.solarwatch.model.sunsetsunrise.SunsetSunrise;
import com.mecsbalint.solarwatch.model.sunsetsunrise.SunsetSunriseResults;
import com.mecsbalint.solarwatch.repository.CityRepository;
import com.mecsbalint.solarwatch.repository.SunsetSunriseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SolarWatchServiceTest {
    private WebClient webClientMock;
    private WebClient.RequestHeadersUriSpec uriSpecMock;
    private WebClient.RequestBodyUriSpec headerSpecMock;
    private WebClient.ResponseSpec responseSpecMock;
    private Mono<City[]> monoCityArrayMock;
    private Mono<SunsetSunrise> monoSunsetSunriseMock;

    private SolarWatchService solarWatchService;
    private final CityRepository cityRepository = mock(CityRepository.class);
    private final SunsetSunriseRepository sunsetSunriseRepository = mock(SunsetSunriseRepository.class);

    @Test
    void getSolarWatchModel_cityNotExist_throwSettlementNotFoundException() {
        webClientMock = mock(WebClient.class);
        uriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);
        headerSpecMock = mock(WebClient.RequestBodyUriSpec.class);
        responseSpecMock = mock(WebClient.ResponseSpec.class);
        monoCityArrayMock = mock(Mono.class);

        when(webClientMock.get()).thenReturn(uriSpecMock);
        when(uriSpecMock.uri(anyString())).thenReturn(headerSpecMock);
        when(headerSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(City[].class)).thenReturn(monoCityArrayMock);
        when(monoCityArrayMock.block()).thenReturn(new City[]{});

        solarWatchService = new SolarWatchService(webClientMock, cityRepository, sunsetSunriseRepository);

        assertThrows(SettlementNotFoundException.class, () -> solarWatchService.getSolarWatchData("non existent city", LocalDate.now()));
    }

    @Test
    void getSolarWatchModel_cityExist_returnSolarWatchModel() {
        webClientMock = mock(WebClient.class);
        uriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);
        headerSpecMock = mock(WebClient.RequestBodyUriSpec.class);
        responseSpecMock = mock(WebClient.ResponseSpec.class);
        monoCityArrayMock = mock(Mono.class);
        monoSunsetSunriseMock = mock(Mono.class);

        when(webClientMock.get()).thenReturn(uriSpecMock);
        when(uriSpecMock.uri(anyString())).thenReturn(headerSpecMock);
        when(headerSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(City[].class)).thenReturn(monoCityArrayMock);
        when(responseSpecMock.bodyToMono(SunsetSunrise.class)).thenReturn(monoSunsetSunriseMock);
        when(monoCityArrayMock.block()).thenReturn(new City[]{new City()});
        when(monoSunsetSunriseMock.block()).thenReturn(new SunsetSunrise(new SunsetSunriseResults("5:12:27 AM", "5:12:27 PM")));

        solarWatchService = new SolarWatchService(webClientMock, cityRepository, sunsetSunriseRepository);

        String expectedSunrise = "5:12:27 AM";
        String actualSunrise = solarWatchService.getSolarWatchData("Budapest", LocalDate.of(2025, 3, 6)).sunRise();

        assertEquals(expectedSunrise, actualSunrise);
    }
}