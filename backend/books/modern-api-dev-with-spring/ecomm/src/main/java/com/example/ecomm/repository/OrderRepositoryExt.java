package com.example.ecomm.repository;

import java.util.Optional;

import com.example.ecomm.entity.OrderEntity;
import com.example.ecomm.model.NewOrder;

public interface OrderRepositoryExt {

    Optional<OrderEntity> insert(NewOrder m);
}
