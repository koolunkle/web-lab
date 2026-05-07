package com.eazybytes.eazystore.service;

import java.util.List;

import com.eazybytes.eazystore.dto.OrderRequestDto;
import com.eazybytes.eazystore.dto.OrderResponseDto;

public interface IOrderService {

    void createOrder(OrderRequestDto orderRequest);

    List<OrderResponseDto> getCustomerOrders();

    List<OrderResponseDto> getAllPendingOrders();

    // Order updateOrderStatus(Long orderId, String orderStatus);

    void updateOrderStatus(Long orderId, String orderStatus);
}
