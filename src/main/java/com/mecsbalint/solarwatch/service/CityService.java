package com.mecsbalint.solarwatch.service;

import com.mecsbalint.solarwatch.controller.dto.CityDto;
import com.mecsbalint.solarwatch.controller.dto.SunsetSunriseDto;
import com.mecsbalint.solarwatch.model.City;
import com.mecsbalint.solarwatch.model.SunsetSunrise;
import com.mecsbalint.solarwatch.repository.CityRepository;
import com.mecsbalint.solarwatch.repository.SunsetSunriseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    private final CityRepository cityRepository;
    private final SunsetSunriseService sunsetSunriseService;

    public CityService(CityRepository cityRepository, SunsetSunriseService sunsetSunriseService) {
        this.cityRepository = cityRepository;
        this.sunsetSunriseService = sunsetSunriseService;
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
        City ogCity = cityRepository.findCityByName(updateCityDto.name());
        if (ogCity == null) {
            return Optional.empty();
        }

        City updateCity = generateCityFromCityDtoWithId(updateCityDto, ogCity.getId());

        City updatedCity = cityRepository.save(updateCity);

        return Optional.of(new CityDto(updatedCity));
    }

    public Optional<CityDto> deleteCity(String name) {
        Optional<City> city = cityRepository.deleteByName(name);
        return city.map(CityDto::new);
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
