package com.example.ecomm.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.ecomm.entity.OrderEntity;

public interface OrderRepository extends CrudRepository<OrderEntity, UUID>, OrderRepositoryExt {

    @EntityGraph(attributePaths = {"userEntity", "addressEntity", "cardEntity", "items"})
    @Query("select o from OrderEntity o join o.userEntity u where u.id = :customerId")
    public Iterable<OrderEntity> findByCustomerId(@Param("customerId") UUID customerId);

    @Override
    @EntityGraph(attributePaths = {"userEntity", "addressEntity", "cardEntity", "items"})
    Optional<OrderEntity> findById(UUID id);
}
