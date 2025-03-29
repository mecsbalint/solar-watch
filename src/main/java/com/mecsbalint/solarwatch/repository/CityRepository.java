package com.mecsbalint.solarwatch.repository;

import com.mecsbalint.solarwatch.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    City findCityById(long id);

    City findCityByName(String name);

    Optional<City> deleteByName(String name);
}
