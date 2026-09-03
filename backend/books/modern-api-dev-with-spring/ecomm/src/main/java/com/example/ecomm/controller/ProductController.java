package com.example.ecomm.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecomm.ProductApi;
import com.example.ecomm.hateoas.ProductRepresentationModelAssembler;
import com.example.ecomm.model.Product;
import com.example.ecomm.service.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ProductController implements ProductApi {

  private final ProductService service;
  private final ProductRepresentationModelAssembler assembler;

  @Override
  public ResponseEntity<Product> getProduct(String id) {
    return service.getProduct(id)
        .map(assembler::toModel)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @Override
  public ResponseEntity<List<Product>> queryProducts(
      String tag,
      String name,
      Integer page,
      Integer size) {
    return ResponseEntity.ok(assembler.toListModel(service.getAllProducts()));
  }
}
