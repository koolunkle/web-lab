package com.eazybytes.eazystore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eazybytes.eazystore.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
