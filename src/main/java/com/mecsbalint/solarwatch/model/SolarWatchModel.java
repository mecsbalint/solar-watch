package com.mecsbalint.solarwatch.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record SolarWatchModel(String city, LocalDate date, String sunRise, String sunSet) {
}
