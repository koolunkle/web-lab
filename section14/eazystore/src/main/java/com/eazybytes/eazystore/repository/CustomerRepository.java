package com.eazybytes.eazystore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eazybytes.eazystore.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
