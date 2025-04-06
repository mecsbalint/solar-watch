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
        if (cityService.addCity(cityAddDto)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PutMapping
    public ResponseEntity<?> updateCity(@RequestBody CityDto updateCityDto) {
        Optional<CityDto> updatedCity = cityService.updateCity(updateCityDto);

        if (updatedCity.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok(updatedCity.get());
    }

    @DeleteMapping("/{name}")
    @Transactional
    public ResponseEntity<?> deleteCity(@PathVariable String name) {
        Optional<CityDto> city = cityService.deleteCity(name);
        if (city.isPresent()) {
            return ResponseEntity.ok(city.get());
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
