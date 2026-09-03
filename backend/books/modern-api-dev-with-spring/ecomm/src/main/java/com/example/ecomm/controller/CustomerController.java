package com.example.ecomm.controller;

import static org.springframework.http.ResponseEntity.notFound;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecomm.CustomerApi;
import com.example.ecomm.hateoas.AddressRepresentationModelAssembler;
import com.example.ecomm.hateoas.CardRepresentationModelAssembler;
import com.example.ecomm.hateoas.UserRepresentationModelAssembler;
import com.example.ecomm.model.Address;
import com.example.ecomm.model.Card;
import com.example.ecomm.model.User;
import com.example.ecomm.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CustomerController implements CustomerApi {

  private final UserRepresentationModelAssembler assembler;
  private final AddressRepresentationModelAssembler addrAssembler;
  private final CardRepresentationModelAssembler cardAssembler;
  private final UserService service;

  @Override
  public ResponseEntity<Void> deleteCustomerById(String id) {
    service.deleteCustomerById(id);
    return ResponseEntity.accepted().build();
  }

  @Override
  public ResponseEntity<List<Address>> getAddressesByCustomerId(String id) {
    return service.getAddressesByCustomerId(id)
        .map(addrAssembler::toListModel)
        .map(ResponseEntity::ok)
        .orElse(notFound().build());
  }

  @Override
  public ResponseEntity<List<User>> getAllCustomers() {
    return ResponseEntity.ok(assembler.toListModel(service.getAllCustomers()));
  }

  @Override
  public ResponseEntity<Card> getCardByCustomerId(String id) {
    return service.getCardByCustomerId(id)
        .map(cardAssembler::toModel)
        .map(ResponseEntity::ok)
        .orElse(notFound().build());
  }

  @Override
  public ResponseEntity<User> getCustomerById(String id) {
    return service.getCustomerById(id)
        .map(assembler::toModel)
        .map(ResponseEntity::ok)
        .orElse(notFound().build());
  }
}
