package com.eazybytes.eazystore.repository;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.eazybytes.eazystore.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Cacheable("roles")
    // ROLE_USER -> CACHE MISS -> DB CALL -> CACHE STORE (ROLE_USER -> Role record) -> CUSTOMER 1
    // ROLE_USER -> CACHE HIT -> CUSTOMER 2
    // ROLE_ADMIN -> CACHE MISS -> DB CALL -> CACHE STORE (ROLE_ADMIN -> Role record) -> CUSTOMER X
    Optional<Role> findByName(String name);
}
