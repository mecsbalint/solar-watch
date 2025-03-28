package com.mecsbalint.solarwatch.service;

import com.mecsbalint.solarwatch.controller.dto.CityDto;
import com.mecsbalint.solarwatch.model.City;
import com.mecsbalint.solarwatch.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public boolean addCity(CityDto cityAddDto) {
        if (cityRepository.findCityByName(cityAddDto.name()) != null) {
            return false;
        }

        City newCity = generateCityFromCityDto(cityAddDto);

        cityRepository.save(newCity);

        return true;
    }

    public Optional<CityDto> updateCity(CityDto updateCityDto) {
        if (cityRepository.findCityByName(updateCityDto.name()) == null) {
            return Optional.empty();
        }

        City updateCity = generateCityFromCityDto(updateCityDto);

        City updatedCity = cityRepository.save(updateCity);

        return Optional.of(new CityDto(updatedCity));
    }

    public Optional<CityDto> deleteCity(String name) {
        Optional<City> city = cityRepository.deleteByName(name);
        return city.map(CityDto::new);
    }

    private City generateCityFromCityDto(CityDto cityDto) {
        City city = new City();
        city.setName(cityDto.name());
        city.setLon(cityDto.lon());
        city.setLat(cityDto.lat());
        city.setCountry(cityDto.country());
        city.setState(cityDto.state());
        city.setSunsetSunrises(cityDto.sunsetSunrises());

        return city;
    }
}
