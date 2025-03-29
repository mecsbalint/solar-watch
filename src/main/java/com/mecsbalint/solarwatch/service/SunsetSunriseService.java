package com.mecsbalint.solarwatch.service;

import com.mecsbalint.solarwatch.controller.dto.SunsetSunriseDto;
import com.mecsbalint.solarwatch.controller.dto.SunsetSunriseNewDto;
import com.mecsbalint.solarwatch.model.City;
import com.mecsbalint.solarwatch.model.SunsetSunrise;
import com.mecsbalint.solarwatch.repository.CityRepository;
import com.mecsbalint.solarwatch.repository.SunsetSunriseRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SunsetSunriseService {

    private final SunsetSunriseRepository sunsetSunriseRepository;
    private final CityRepository cityRepository;

    public SunsetSunriseService(SunsetSunriseRepository sunsetSunriseRepository, CityRepository cityRepository) {
        this.sunsetSunriseRepository = sunsetSunriseRepository;
        this.cityRepository = cityRepository;
    }

    public boolean addSunsetSunrise(SunsetSunriseNewDto sunsetSunriseNewDto) {
        City city = cityRepository.findCityById(sunsetSunriseNewDto.cityId());
        if (city == null) return false;

        if (sunsetSunriseRepository.findSunsetSunriseByCityAndDate(city, sunsetSunriseNewDto.date()) != null) return false;

        SunsetSunrise newSunsetSunrise = generateSunsetSunriseFromSunsetSunriseNewDto(sunsetSunriseNewDto, city);

        sunsetSunriseRepository.save(newSunsetSunrise);

        return true;
    }

    public Optional<SunsetSunriseDto> updateSunsetSunrise(SunsetSunriseDto sunsetSunriseDto) {
        City city = cityRepository.findCityById(sunsetSunriseDto.cityId());
        if (city == null) return Optional.empty();

        if (sunsetSunriseRepository.existsById(sunsetSunriseDto.id())) return Optional.empty();

        SunsetSunrise updatedSunsetSunrise = generateSunsetSunriseFromSunsetSunriseDto(sunsetSunriseDto, city);

        sunsetSunriseRepository.save(updatedSunsetSunrise);

        return Optional.of(new SunsetSunriseDto(updatedSunsetSunrise));
    }

    public Optional<SunsetSunriseDto> deleteSunsetSunrise(long id) {
        Optional<SunsetSunrise> sunsetSunriseOpt = sunsetSunriseRepository.deleteById(id);

        return sunsetSunriseOpt.map(SunsetSunriseDto::new);
    }

    private SunsetSunrise generateSunsetSunriseFromSunsetSunriseNewDto(SunsetSunriseNewDto sunsetSunriseNewDto, City city) {
        SunsetSunrise sunsetSunrise = new SunsetSunrise();

        sunsetSunrise.setCity(city);
        sunsetSunrise.setDate(sunsetSunriseNewDto.date());
        sunsetSunrise.setSunrise(sunsetSunriseNewDto.sunrise());
        sunsetSunrise.setSunset(sunsetSunriseNewDto.sunrise());

        return sunsetSunrise;
    }

    public SunsetSunrise generateSunsetSunriseFromSunsetSunriseDto(SunsetSunriseDto sunsetSunriseDto, City city) {
        SunsetSunrise sunsetSunrise = new SunsetSunrise();

        sunsetSunrise.setId(sunsetSunriseDto.id());
        sunsetSunrise.setCity(city);
        sunsetSunrise.setDate(sunsetSunriseDto.date());
        sunsetSunrise.setSunrise(sunsetSunriseDto.sunrise());
        sunsetSunrise.setSunset(sunsetSunriseDto.sunrise());

        return sunsetSunrise;
    }
}
