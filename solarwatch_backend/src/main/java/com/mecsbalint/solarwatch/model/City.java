package com.mecsbalint.solarwatch.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Entity
@Data
public class City {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ID_SEQ")
    private long id;

    @Column(unique = true)
    private String name;
    private double lon;
    private double lat;
    private String country;
    private String state;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SunsetSunrise> sunsetSunrises;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return id == city.id && Double.compare(lon, city.lon) == 0 && Double.compare(lat, city.lat) == 0 && Objects.equals(name, city.name) && Objects.equals(country, city.country) && Objects.equals(state, city.state) && Objects.equals(sunsetSunrises, city.sunsetSunrises);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lon, lat, country, state, sunsetSunrises);
    }
}
