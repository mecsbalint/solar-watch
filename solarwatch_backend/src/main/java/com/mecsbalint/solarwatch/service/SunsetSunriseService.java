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
import org.springframework.stereotype.Service;


@Service
public class SunsetSunriseService {

    private final SunsetSunriseRepository sunsetSunriseRepository;
    private final CityRepository cityRepository;

    public SunsetSunriseService(SunsetSunriseRepository sunsetSunriseRepository, CityRepository cityRepository) {
        this.sunsetSunriseRepository = sunsetSunriseRepository;
        this.cityRepository = cityRepository;
    }

    public void addSunsetSunrise(SunsetSunriseNewDto sunsetSunriseNewDto) {
        City city = cityRepository.findCityById(sunsetSunriseNewDto.cityId());

        if (city == null) {
            throw new SettlementNotFoundException("n/a", "SolarWatch database");
        }

        if (sunsetSunriseRepository.findSunsetSunriseByCityAndDate(city, sunsetSunriseNewDto.date()) != null) {
            throw new SunriseSunsetAlreadyExistException(city.getName(), sunsetSunriseNewDto.date());
        }

        SunsetSunrise newSunsetSunrise = generateSunsetSunriseFromSunsetSunriseNewDto(sunsetSunriseNewDto, city);

        sunsetSunriseRepository.save(newSunsetSunrise);

    }

    public SunsetSunriseDto updateSunsetSunrise(SunsetSunriseDto sunsetSunriseDto) {
        City city = cityRepository.findCityById(sunsetSunriseDto.cityId());

        if (city == null) {
            throw new SettlementNotFoundException("n/a", "SolarWatch Database");
        }

        if (!sunsetSunriseRepository.existsById(sunsetSunriseDto.id())) {
            throw new SunriseSunsetNotFoundException();
        }

        SunsetSunrise updatedSunsetSunrise = generateSunsetSunriseFromSunsetSunriseDto(sunsetSunriseDto, city);

        sunsetSunriseRepository.save(updatedSunsetSunrise);

        return new SunsetSunriseDto(updatedSunsetSunrise);
    }

    public SunsetSunriseDto deleteSunsetSunrise(long id) {
        SunsetSunrise sunsetSunrise = sunsetSunriseRepository.findSunsetSunriseById(id);

        if (sunsetSunrise == null) {
            throw new SunriseSunsetNotFoundException();
        }

        sunsetSunriseRepository.deleteById(id);

        return new SunsetSunriseDto(sunsetSunrise);
    }

    private SunsetSunrise generateSunsetSunriseFromSunsetSunriseNewDto(SunsetSunriseNewDto sunsetSunriseNewDto, City city) {
        SunsetSunrise sunsetSunrise = new SunsetSunrise();

        sunsetSunrise.setCity(city);
        sunsetSunrise.setDate(sunsetSunriseNewDto.date());
        sunsetSunrise.setSunrise(sunsetSunriseNewDto.sunrise());
        sunsetSunrise.setSunset(sunsetSunriseNewDto.sunset());

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
