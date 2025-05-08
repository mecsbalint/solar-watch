package com.mecsbalint.solarwatch.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Data
public class SunsetSunrise {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="ID_SEQ")
    private long id;

    @ManyToOne
    private City city;
    private LocalDate date;
    private String sunrise;
    private String sunset;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SunsetSunrise that = (SunsetSunrise) o;
        return id == that.id && Objects.equals(city, that.city) && Objects.equals(date, that.date) && Objects.equals(sunrise, that.sunrise) && Objects.equals(sunset, that.sunset);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city, date, sunrise, sunset);
    }
}
