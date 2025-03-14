package com.mecsbalint.solarwatch.repository;

import com.mecsbalint.solarwatch.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    City findCityByName(String name);
}
