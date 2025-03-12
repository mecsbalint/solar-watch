package com.mecsbalint.solarwatch.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class City {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ID_SEQ")
    private long id;
    private String name;
    private double lon;
    private double lat;
    private String country;
    private String state;

    @OneToMany(mappedBy = "city")
    private List<SunsetSunrise> sunsetSunrises;

    public String getName() {
        return name;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public List<SunsetSunrise> getSunsetSunrises() {
        return sunsetSunrises;
    }
}
