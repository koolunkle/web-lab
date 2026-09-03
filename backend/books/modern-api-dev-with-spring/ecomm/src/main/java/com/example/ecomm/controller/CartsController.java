package com.example.ecomm.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecomm.CartApi;
import com.example.ecomm.hateoas.CartRepresentationModelAssembler;
import com.example.ecomm.model.Cart;
import com.example.ecomm.model.Item;
import com.example.ecomm.service.CartService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CartsController implements CartApi {

    private static final Logger log = LoggerFactory.getLogger(CartsController.class);
    private final CartService service;
    private final CartRepresentationModelAssembler assembler;

    @Override
    public ResponseEntity<List<Item>> addCartItemsByCustomerId(String customerId, Item item) {
        log.debug("Request for customer ID: {}\nItem: {}", customerId, item);
        return ResponseEntity.ok(service.addCartItemsByCustomerId(customerId, item));
    }

    @Override
    public ResponseEntity<List<Item>> addOrReplaceItemsByCustomerId(String customerId,
            Item item) {
        return ResponseEntity.ok(service.addOrReplaceItemsByCustomerId(customerId, item));
    }

    @Override
    public ResponseEntity<Void> deleteCart(String customerId) {
        service.deleteCart(customerId);
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<Void> deleteItemFromCart(String customerId, String itemId) {
        service.deleteItemFromCart(customerId, itemId);
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<Cart> getCartByCustomerId(String customerId) {
        return ResponseEntity.ok(assembler.toModel(service.getCartByCustomerId(customerId)));
    }

    @Override
    public ResponseEntity<List<Item>> getCartItemsByCustomerId(String customerId) {
        return ResponseEntity.ok(service.getCartItemsByCustomerId(customerId));
    }

    @Override
    public ResponseEntity<Item> getCartItemsByItemId(String customerId, String itemId) {
        return ResponseEntity.ok(service.getCartItemsByItemId(customerId, itemId));
    }
}
