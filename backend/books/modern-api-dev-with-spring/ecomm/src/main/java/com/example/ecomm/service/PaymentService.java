package com.example.ecomm.service;

import java.util.Optional;

import com.example.ecomm.entity.AuthorizationEntity;
import com.example.ecomm.model.PaymentReq;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface PaymentService {

  Optional<AuthorizationEntity> authorize(@Valid PaymentReq paymentReq);

  Optional<AuthorizationEntity> getOrdersPaymentAuthorization(@NotNull String orderId);
}
