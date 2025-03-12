package com.mecsbalint.solarwatch.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class SunsetSunrise {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="ID_SEQ")
    private long id;

    @ManyToOne
    private City city;
    private LocalDate date;
    private String sunrise;
    private String sunset;

    public City getCity() {
        return city;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }
}
