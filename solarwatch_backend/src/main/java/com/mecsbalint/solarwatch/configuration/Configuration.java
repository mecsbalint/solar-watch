package com.mecsbalint.solarwatch.configuration;

import com.mecsbalint.solarwatch.model.RoleType;
import com.mecsbalint.solarwatch.model.UserRole;
import com.mecsbalint.solarwatch.repository.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public WebClient getWebClient() {
        return WebClient.create();
    }

    @Bean
    public CommandLineRunner initCategories(UserRoleRepository userRoleRepository) {
        return args -> {
            if (userRoleRepository.count() == 0) {
                for (RoleType type : RoleType.values()) {
                    userRoleRepository.save(new UserRole(type));
                }
                userRoleRepository.flush();
            }
        };
    }
}
