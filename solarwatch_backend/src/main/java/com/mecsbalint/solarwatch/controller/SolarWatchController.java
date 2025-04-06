package com.mecsbalint.solarwatch.controller;

import com.mecsbalint.solarwatch.exceptions.MissingQueryParamException;
import com.mecsbalint.solarwatch.controller.dto.SolarWatchDto;
import com.mecsbalint.solarwatch.service.SolarWatchService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("api/solarwatch")
public class SolarWatchController {
    private final SolarWatchService solarWatchService;

    public SolarWatchController(SolarWatchService solarWatchService) {
        this.solarWatchService = solarWatchService;
    }

    @GetMapping
    public SolarWatchDto getSolarWatchDataForCity(@RequestParam(required = false) String city, @RequestParam(required = false) String date) {
        if (city == null) throw new MissingQueryParamException();
        LocalDate dateObj = date == null ? LocalDate.now() : LocalDate.parse(date);

        return solarWatchService.getSolarWatchData(city, dateObj);
    }
}
