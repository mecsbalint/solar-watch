package com.mecsbalint.solarwatch.service;

import com.mecsbalint.solarwatch.controller.dto.UserDto;
import com.mecsbalint.solarwatch.controller.dto.UserNamePasswordDto;
import com.mecsbalint.solarwatch.model.UserEntity;
import com.mecsbalint.solarwatch.model.UserRole;
import com.mecsbalint.solarwatch.repository.UserRepository;
import com.mecsbalint.solarwatch.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public boolean saveUser(UserNamePasswordDto userRegistration) {
        if (userRepository.findByName(userRegistration.name()).isPresent()) {
            return false;
        }

        UserEntity user = new UserEntity();
        user.setName(userRegistration.name());
        user.setPassword(passwordEncoder.encode(userRegistration.password()));
        user.setRoles(Set.of(userRoleRepository.findUserRoleByRoleType(UserRole.RoleType.ROLE_USER)));

        userRepository.save(user);

        return true;
    }
}
