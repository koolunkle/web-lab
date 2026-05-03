package com.eazybytes.eazystore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eazybytes.eazystore.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmailOrMobileNumber(String email, String mobileNumber);
}
