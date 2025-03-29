package com.mecsbalint.solarwatch.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserRole {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="ID_SEQ")
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleType roleType;

    public UserRole(RoleType roleType) {
        this.roleType = roleType;
    }

    public UserRole() {
    }

    public enum RoleType {
        ROLE_USER,
        ROLE_ADMIN;
    }
}
