package com.mecsbalint.solarwatch.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

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
}
