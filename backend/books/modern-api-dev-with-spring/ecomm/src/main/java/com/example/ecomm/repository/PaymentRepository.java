package com.example.ecomm.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.example.ecomm.entity.PaymentEntity;

public interface PaymentRepository extends CrudRepository<PaymentEntity, UUID> {
}
