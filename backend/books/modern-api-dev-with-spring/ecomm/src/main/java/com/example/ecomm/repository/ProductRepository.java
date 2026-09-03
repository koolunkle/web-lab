package com.example.ecomm.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import com.example.ecomm.entity.ProductEntity;

public interface ProductRepository extends CrudRepository<ProductEntity, UUID> {

    @Override
    @EntityGraph(attributePaths = "tags")
    Iterable<ProductEntity> findAll();

    @Override
    @EntityGraph(attributePaths = "tags")
    Optional<ProductEntity> findById(UUID id);
}
