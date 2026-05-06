package com.eazybytes.eazystore.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.eazybytes.eazystore.constants.ApplicationConstants;
import com.eazybytes.eazystore.dto.OrderItemResponseDto;
import com.eazybytes.eazystore.dto.OrderRequestDto;
import com.eazybytes.eazystore.dto.OrderResponseDto;
import com.eazybytes.eazystore.entity.Customer;
import com.eazybytes.eazystore.entity.Order;
import com.eazybytes.eazystore.entity.OrderItem;
import com.eazybytes.eazystore.entity.Product;
import com.eazybytes.eazystore.exception.ResourceNotFoundException;
import com.eazybytes.eazystore.repository.OrderRepository;
import com.eazybytes.eazystore.repository.ProductRepository;
import com.eazybytes.eazystore.service.IOrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ProfileServiceImpl profileService;

    @Override
    public void createOrder(OrderRequestDto orderRequest) {
        Customer customer = profileService.getAuthenticatedCustomer();

        // Create Order
        Order order = new Order();

        order.setCustomer(customer);
        BeanUtils.copyProperties(orderRequest, order);
        order.setOrderStatus(ApplicationConstants.ORDER_STATUS_CREATED);

        // Map OrderItems
        List<OrderItem> orderItems = orderRequest.items().stream().map(item -> {
            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);

            Product product = productRepository.findById(item.productId()).orElseThrow(
                    () -> new ResourceNotFoundException("Product", "ProductID", item.productId().toString()));

            orderItem.setProduct(product);
            orderItem.setQuantity(item.quantity());
            orderItem.setPrice(item.price());

            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        orderRepository.save(order);
    }

    @Override
    public List<OrderResponseDto> getCustomerOrders() {
        Customer customer = profileService.getAuthenticatedCustomer();
        List<Order> orders = orderRepository.findByCustomerOrderByCreatedAtDesc(customer);

        return orders.stream().map(this::mapToOrderResponseDTO).collect(Collectors.toList());
    }

    /**
     * Map Order entity to OrderResponseDto
     */
    private OrderResponseDto mapToOrderResponseDTO(Order order) {
        // Map Order Items
        List<OrderItemResponseDto> itemDtos = order.getOrderItems().stream()
                .map(this::mapToOrderItemResponseDTO)
                .collect(Collectors.toList());

        OrderResponseDto orderResponseDto = new OrderResponseDto(order.getOrderId(), order.getOrderStatus(),
                order.getTotalPrice(), order.getCreatedAt().toString(), itemDtos);

        return orderResponseDto;
    }

    /**
     * Map OrderItem entity to OrderItemResponseDto
     */
    private OrderItemResponseDto mapToOrderItemResponseDTO(OrderItem orderItem) {
        OrderItemResponseDto itemDto = new OrderItemResponseDto(
                orderItem.getProduct().getName(), orderItem.getQuantity(), orderItem.getPrice(),
                orderItem.getProduct().getImageUrl());

        return itemDto;
    }
}
