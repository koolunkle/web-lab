package com.eazybytes.eazystore.service;

import java.util.List;

import com.eazybytes.eazystore.dto.ProductDto;

public interface IProductService {

    List<ProductDto> getProducts();

    ProductDto getProductById(Long productId);
}
