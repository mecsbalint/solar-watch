package com.mecsbalint.solarwatch.service;

import com.mecsbalint.solarwatch.controller.dto.CityDto;
import com.mecsbalint.solarwatch.exceptions.SettlementAlreadyExistException;
import com.mecsbalint.solarwatch.exceptions.SettlementNotFoundException;
import com.mecsbalint.solarwatch.model.City;
import com.mecsbalint.solarwatch.model.SunsetSunrise;
import com.mecsbalint.solarwatch.repository.CityRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;
    private final SunsetSunriseService sunsetSunriseService;

    public CityService(CityRepository cityRepository, SunsetSunriseService sunsetSunriseService) {
        this.cityRepository = cityRepository;
        this.sunsetSunriseService = sunsetSunriseService;
    }

    public void addCity(CityDto cityAddDto) {
        if (cityRepository.findCityByName(cityAddDto.name()) != null) {
           throw new SettlementAlreadyExistException(cityAddDto.name());
        }

        City newCity = generateCityFromCityDto(cityAddDto);
        cityRepository.save(newCity);
    }

    public CityDto updateCity(CityDto updateCityDto) {
        City ogCity = cityRepository.findCityByName(updateCityDto.name());
        if (ogCity == null) {
            throw new SettlementNotFoundException(updateCityDto.name(), "SolarWatch database");
        }

        City updateCity = generateCityFromCityDtoWithId(updateCityDto, ogCity.getId());

        City updatedCity = cityRepository.save(updateCity);

        return new CityDto(updatedCity);
    }

    @Transactional
    public CityDto deleteCity(String name) {
        City city = cityRepository.findCityByName(name);

        if (city == null) {
            throw new SettlementNotFoundException(name, "SolarWatch database");
        }

        cityRepository.deleteByName(name);

        return new CityDto(city);
    }

    private City generateCityFromCityDto(CityDto cityDto) {
        City city = new City();
        List<SunsetSunrise> sunsetSunrises = cityDto.sunsetSunrises().stream()
                        .map(sunsetSunriseDto -> sunsetSunriseService.generateSunsetSunriseFromSunsetSunriseDto(sunsetSunriseDto, city))
                        .toList();

        city.setName(cityDto.name());
        city.setLon(cityDto.lon());
        city.setLat(cityDto.lat());
        city.setCountry(cityDto.country());
        city.setState(cityDto.state());
        city.setSunsetSunrises(sunsetSunrises);

        return city;
    }

    private City generateCityFromCityDtoWithId(CityDto cityDto, long id) {
        City city = generateCityFromCityDto(cityDto);
        city.setId(id);

        return city;
    }
}
