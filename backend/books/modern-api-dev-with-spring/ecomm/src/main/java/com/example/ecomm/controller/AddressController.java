package com.example.ecomm.controller;

import static org.springframework.http.ResponseEntity.notFound;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecomm.AddressApi;
import com.example.ecomm.entity.RoleEnum.Const;
import com.example.ecomm.hateoas.AddressRepresentationModelAssembler;
import com.example.ecomm.model.AddAddressReq;
import com.example.ecomm.model.Address;
import com.example.ecomm.service.AddressService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AddressController implements AddressApi {

  private final AddressService service;
  private final AddressRepresentationModelAssembler assembler;

  @Override
  public ResponseEntity<Address> createAddress(AddAddressReq addAddressReq) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(service.createAddress(addAddressReq).map(assembler::toModel).get());
  }

  @PreAuthorize("hasRole('" + Const.ADMIN + "')")
  @Override
  public ResponseEntity<Void> deleteAddressesById(String id) {
    service.deleteAddressesById(id);
    return ResponseEntity.accepted().build();
  }

  @Override
  public ResponseEntity<Address> getAddressesById(String id) {
    return service.getAddressesById(id)
        .map(assembler::toModel)
        .map(ResponseEntity::ok)
        .orElse(notFound().build());
  }

  @Override
  public ResponseEntity<List<Address>> getAllAddresses() {
    return ResponseEntity.ok(assembler.toListModel(service.getAllAddresses()));
  }
}
