package com.mecsbalint.solarwatch.security.service;

import com.mecsbalint.solarwatch.model.UserEntity;
import com.mecsbalint.solarwatch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private  final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByName(username).orElseThrow(() -> new UsernameNotFoundException(username));

        List<SimpleGrantedAuthority> roles = userEntity.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleType().name()))
                .toList();

        return new User(userEntity.getName(), userEntity.getPassword(), roles);
    }
}
