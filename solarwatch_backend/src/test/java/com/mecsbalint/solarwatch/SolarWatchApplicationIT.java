package com.mecsbalint.solarwatch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mecsbalint.solarwatch.controller.dto.JwtResponseDto;
import com.mecsbalint.solarwatch.controller.dto.UserNamePasswordDto;
import com.mecsbalint.solarwatch.model.City;
import com.mecsbalint.solarwatch.model.sunsetsunrise.SunsetSunrise;
import com.mecsbalint.solarwatch.model.sunsetsunrise.SunsetSunriseResults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SolarWatchApplication.class, webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties", properties = "GEOCODING_KEY=nothing")
public class SolarWatchApplicationIT {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private WebClient webClient;

    @Test
    public void registration_usernameIsNotOccupied_responseStatus200() throws Exception {
        var usernamePasswordDto = new UserNamePasswordDto("Kitten", "password");

        mvc.perform(post("/api/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usernamePasswordDto)))
                .andExpect(status().isCreated());
    }

    @Test
    public void login_usernameDoesAndPasswordExist_responseStatus201() throws Exception {
        var usernamePasswordDto = new UserNamePasswordDto("Boci", "12345");

        mvc.perform(post("/api/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usernamePasswordDto)));

        mvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usernamePasswordDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void getSolarWatchData_userLoggedInAndCityExists_responseStatus200() throws Exception {
        var usernamePasswordDto = new UserNamePasswordDto("User", "abcde");

        WebClient.RequestHeadersUriSpec uriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestBodyUriSpec headerSpecMock = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.ResponseSpec responseSpecMock = mock(WebClient.ResponseSpec.class);
        Mono<City[]> monoCityArrayMock = mock(Mono.class);
        Mono<SunsetSunrise> monoSunsetSunriseMock = mock(Mono.class);

        when(webClient.get()).thenReturn(uriSpecMock);
        when(uriSpecMock.uri(anyString())).thenReturn(headerSpecMock);
        when(headerSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(City[].class)).thenReturn(monoCityArrayMock);
        when(responseSpecMock.bodyToMono(SunsetSunrise.class)).thenReturn(monoSunsetSunriseMock);
        when(monoCityArrayMock.block()).thenReturn(new City[]{new City()});
        when(monoSunsetSunriseMock.block()).thenReturn(new SunsetSunrise(new SunsetSunriseResults("5:12:27 AM", "5:12:27 PM")));

        mvc.perform(post("/api/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usernamePasswordDto)));

        String responseBody = mvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usernamePasswordDto)))
                .andReturn().getResponse().getContentAsString();

        JwtResponseDto jwtResponseDto = objectMapper.createParser(responseBody).readValueAs(JwtResponseDto.class);
        String jwtToken = jwtResponseDto.jwt();

        mvc.perform(get("/api/solarwatch?city=Budapest")
                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }
}
