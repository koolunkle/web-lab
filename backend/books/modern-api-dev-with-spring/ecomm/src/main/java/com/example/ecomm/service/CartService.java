package com.example.ecomm.service;

import java.util.List;

import com.example.ecomm.entity.CartEntity;
import com.example.ecomm.model.Item;

import jakarta.validation.Valid;

public interface CartService {

  List<Item> addCartItemsByCustomerId(String customerId, @Valid Item item);

  List<Item> addOrReplaceItemsByCustomerId(String customerId, @Valid Item item);

  void deleteCart(String customerId);

  void deleteItemFromCart(String customerId, String itemId);

  CartEntity getCartByCustomerId(String customerId);

  List<Item> getCartItemsByCustomerId(String customerId);

  Item getCartItemsByItemId(String customerId, String itemId);
}
