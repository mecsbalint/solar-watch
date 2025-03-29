package com.mecsbalint.solarwatch.controller.dto;

import com.mecsbalint.solarwatch.model.UserEntity;
import com.mecsbalint.solarwatch.model.UserRole;

import java.util.Set;

public record UserDto(long id, String name, String password, Set<UserRole> roles) {
    public UserDto(UserEntity user) {
        this(user.getId(), user.getName(), user.getPassword(), user.getRoles());
    }
}
