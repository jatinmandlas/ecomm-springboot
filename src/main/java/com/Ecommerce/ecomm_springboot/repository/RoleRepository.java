package com.Ecommerce.ecomm_springboot.repository;

import com.Ecommerce.ecomm_springboot.model.AppRole;
import com.Ecommerce.ecomm_springboot.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByRoleName(AppRole roleName);
}
