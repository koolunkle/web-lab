package com.example.ecomm.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.ecomm.entity.CartEntity;

public interface CartRepository extends CrudRepository<CartEntity, UUID> {

    @EntityGraph(attributePaths = "items")
    @Query("select c from CartEntity c join c.user u where u.id = :customerId")
    public Optional<CartEntity> findByCustomerId(@Param("customerId") UUID customerId);
}
