package com.example.ecomm.service;

import java.util.Optional;

import org.springframework.validation.annotation.Validated;

import com.example.ecomm.entity.ProductEntity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Validated
public interface ProductService {

  @NotNull
  Iterable<ProductEntity> getAllProducts();

  Optional<ProductEntity> getProduct(@NotBlank(message = "Invalid product ID.") String id);
}