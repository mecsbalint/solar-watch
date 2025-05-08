package com.mecsbalint.solarwatch.repository;

import com.mecsbalint.solarwatch.model.RoleType;
import com.mecsbalint.solarwatch.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findUserRoleById(long id);

    UserRole findUserRoleByRoleType(RoleType roleType);
}
