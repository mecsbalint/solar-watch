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
public class SunsetSunriseController {

    private final SunsetSunriseService sunsetSunriseService;

    public SunsetSunriseController(SunsetSunriseService sunsetSunriseService) {
        this.sunsetSunriseService = sunsetSunriseService;
    }

    @PostMapping
    public ResponseEntity<Void> addSunsetSunrise(@RequestBody SunsetSunriseNewDto sunsetSunriseNewDto) {
        sunsetSunriseService.addSunsetSunrise(sunsetSunriseNewDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public SunsetSunriseDto updateSunsetSunrise(@RequestBody SunsetSunriseDto sunsetSunriseDto) {
        return sunsetSunriseService.updateSunsetSunrise(sunsetSunriseDto);
    }

    @DeleteMapping("/{id}")
    public SunsetSunriseDto deleteSunsetSunrise(@PathVariable long id) {
        return sunsetSunriseService.deleteSunsetSunrise(id);

    }
}
