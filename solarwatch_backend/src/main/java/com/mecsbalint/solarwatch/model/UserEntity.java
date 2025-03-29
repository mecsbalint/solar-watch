package com.mecsbalint.solarwatch.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SEQ")
    private long id;

    @Column(unique = true)
    private String name;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<UserRole> roles;
}
