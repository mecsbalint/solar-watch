package com.mecsbalint.solarwatch.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
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
}
