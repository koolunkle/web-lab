package com.example.ecomm.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.example.ecomm.entity.AuthorizationEntity;

public interface AuthorizationRepository extends CrudRepository<AuthorizationEntity, UUID> {

    Optional<AuthorizationEntity> findByOrderEntityId(UUID orderId);
}
