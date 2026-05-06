package com.eazybytes.eazystore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eazybytes.eazystore.entity.Customer;
import com.eazybytes.eazystore.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Fetch orders for a customer, sorted by creation date in descending order.
     */
    List<Order> findByCustomerOrderByCreatedAtDesc(Customer customer);
}
