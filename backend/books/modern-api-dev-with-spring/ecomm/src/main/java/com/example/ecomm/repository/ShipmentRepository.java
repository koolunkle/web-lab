package com.example.ecomm.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.ecomm.entity.ShipmentEntity;

public interface ShipmentRepository extends CrudRepository<ShipmentEntity, UUID> {

    @Query(value = "select s.* from ecomm.orders o, ecomm.shipment s where o.shipment_id = s.id and o.id = :orderId", nativeQuery = true)
    Iterable<ShipmentEntity> getShipmentByOrderId(@Param("orderId") UUID orderId);
}
