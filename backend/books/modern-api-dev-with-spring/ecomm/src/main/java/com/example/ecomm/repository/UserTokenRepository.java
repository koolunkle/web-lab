package com.example.ecomm.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.example.ecomm.entity.UserTokenEntity;

public interface UserTokenRepository extends CrudRepository<UserTokenEntity, UUID> {

    Optional<UserTokenEntity> findByRefreshToken(String refreshToken);
    
    Optional<UserTokenEntity> deleteByUserId(UUID userId);
}
