package com.example.ecomm.service;

import com.example.ecomm.entity.ShipmentEntity;

import jakarta.validation.constraints.NotBlank;

public interface ShipmentService {

  Iterable<ShipmentEntity> getShipmentByOrderId(@NotBlank(message = "Invalid order ID.") String id);
}
