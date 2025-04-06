package com.mecsbalint.solarwatch.controller;

import com.mecsbalint.solarwatch.controller.dto.SunsetSunriseDto;
import com.mecsbalint.solarwatch.controller.dto.SunsetSunriseNewDto;
import com.mecsbalint.solarwatch.service.SunsetSunriseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/sunrise-sunset")
public class SunriseSunsetController {

    private final SunsetSunriseService sunsetSunriseService;

    public SunriseSunsetController(SunsetSunriseService sunsetSunriseService) {
        this.sunsetSunriseService = sunsetSunriseService;
    }

    @PostMapping
    public ResponseEntity<Void> addSunsetSunrise(@RequestBody SunsetSunriseNewDto sunsetSunriseNewDto) {
        if (sunsetSunriseService.addSunsetSunrise(sunsetSunriseNewDto)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PutMapping
    public ResponseEntity<?> updateSunsetSunrise(@RequestBody SunsetSunriseDto sunsetSunriseDto) {
        Optional<SunsetSunriseDto> sunsetSunriseDtoOptional = sunsetSunriseService.updateSunsetSunrise(sunsetSunriseDto);

        if (sunsetSunriseDtoOptional.isEmpty()) return ResponseEntity.status(HttpStatus.CONFLICT).build();

        return ResponseEntity.ok(sunsetSunriseDtoOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSunsetSunrise(@PathVariable long id) {
        Optional<SunsetSunriseDto> sunsetSunriseDtoOpt = sunsetSunriseService.deleteSunsetSunrise(id);

        if (sunsetSunriseDtoOpt.isPresent()) return ResponseEntity.ok(sunsetSunriseDtoOpt.get());

        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
