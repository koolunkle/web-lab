package com.example.ecomm.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecomm.PaymentApi;
import com.example.ecomm.model.Authorization;
import com.example.ecomm.model.PaymentReq;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class PaymentController implements PaymentApi {

  @Override
  public ResponseEntity<Authorization> authorize(PaymentReq paymentReq) {
    return null;
  }

  @Override
  public ResponseEntity<Authorization> getOrdersPaymentAuthorization(String id) {
    return null;
  }
}
