package com.example.ecomm.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.ecomm.entity.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, UUID> {

    @Override
    @EntityGraph(attributePaths = { "addresses", "cards" })
    Optional<UserEntity> findById(UUID id);

    Optional<UserEntity> findByUsername(String username);

    @Query(value = "select count(*) from ecomm.\"user\" u where u.username = :username or u.email = :email", nativeQuery = true)
    Integer findByUsernameOrEmail(String username, String email);
}
