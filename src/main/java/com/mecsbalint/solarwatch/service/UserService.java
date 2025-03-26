package com.mecsbalint.solarwatch.service;

import com.mecsbalint.solarwatch.controller.dto.UserDto;
import com.mecsbalint.solarwatch.model.UserEntity;
import com.mecsbalint.solarwatch.model.UserRole;
import com.mecsbalint.solarwatch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void updateUser(UserDto userDto) {
        UserEntity user = generateUserEntityFromUserDto(userDto);

        userRepository.save(user);
    }

    public void addRoleFor(UserDto userDto, UserRole role) {
        Set<UserRole> oldRoles = userDto.roles();

        Set<UserRole> updatedRoles = new HashSet<>(oldRoles);

        if (updatedRoles.add(role)) updateUser(new UserDto(userDto.id(), userDto.name(), userDto.password(), updatedRoles));
    }

    private UserEntity generateUserEntityFromUserDto(UserDto userDto) {
        UserEntity userEntity = new UserEntity();

        userEntity.setId(userDto.id());
        userEntity.setName(userDto.name());
        userEntity.setPassword(userDto.password());
        userEntity.setRoles(userDto.roles());

        return userEntity;
    }
}
