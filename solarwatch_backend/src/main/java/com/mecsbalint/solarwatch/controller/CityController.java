package com.mecsbalint.solarwatch.controller;

import com.mecsbalint.solarwatch.controller.dto.CityDto;
import com.mecsbalint.solarwatch.service.CityService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/city")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping
    public ResponseEntity<Void> addCity(@RequestBody CityDto cityAddDto) {
        cityService.addCity(cityAddDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public CityDto updateCity(@RequestBody CityDto updateCityDto) {
        return cityService.updateCity(updateCityDto);
    }

    @DeleteMapping("/{name}")
    public CityDto deleteCity(@PathVariable String name) {
        return cityService.deleteCity(name);
    }
}
