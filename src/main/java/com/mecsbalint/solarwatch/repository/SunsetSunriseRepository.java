package com.mecsbalint.solarwatch.repository;

import com.mecsbalint.solarwatch.model.City;
import com.mecsbalint.solarwatch.model.SunsetSunrise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface SunsetSunriseRepository extends JpaRepository<SunsetSunrise, Long> {

    SunsetSunrise findSunsetSunriseByCityAndDate(City city, LocalDate date);
}
