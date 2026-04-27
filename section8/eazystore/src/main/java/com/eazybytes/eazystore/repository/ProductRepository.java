package com.eazybytes.eazystore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eazybytes.eazystore.entity.Product;

@Repository // Optional
public interface ProductRepository extends JpaRepository<Product, Long> {
}