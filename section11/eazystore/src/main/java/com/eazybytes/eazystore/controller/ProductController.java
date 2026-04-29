package com.eazybytes.eazystore.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.eazystore.dto.ProductDto;
import com.eazybytes.eazystore.service.IProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
// @CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    private final IProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() throws InterruptedException {
        List<ProductDto> productList = productService.getProducts();
        // return ResponseEntity.status(HttpStatus.CREATED).body(productList);
        return ResponseEntity.ok(productList);
    }
}
