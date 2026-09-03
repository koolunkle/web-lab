package com.example.ecomm.service;

import java.util.Optional;

import com.example.ecomm.entity.OrderEntity;
import com.example.ecomm.model.NewOrder;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface OrderService {

  Optional<OrderEntity> addOrder(@Valid NewOrder newOrder);

  Iterable<OrderEntity> getOrdersByCustomerId(@NotNull @Valid String customerId);

  Optional<OrderEntity> getByOrderId(String id);
}
