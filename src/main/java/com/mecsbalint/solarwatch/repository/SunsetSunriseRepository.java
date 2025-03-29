package com.mecsbalint.solarwatch.repository;

import com.mecsbalint.solarwatch.controller.dto.SunsetSunriseDto;
import com.mecsbalint.solarwatch.model.City;
import com.mecsbalint.solarwatch.model.SunsetSunrise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SunsetSunriseRepository extends JpaRepository<SunsetSunrise, Long> {

    SunsetSunrise findSunsetSunriseById(long id);

    Optional<SunsetSunrise> deleteById(long id);

    SunsetSunrise findSunsetSunriseByCityAndDate(City city, LocalDate date);
}
