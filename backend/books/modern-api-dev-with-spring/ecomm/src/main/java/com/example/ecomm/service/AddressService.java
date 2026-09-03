package com.example.ecomm.service;

import java.util.Optional;

import com.example.ecomm.entity.AddressEntity;
import com.example.ecomm.model.AddAddressReq;

public interface AddressService {
  
  Optional<AddressEntity> createAddress(AddAddressReq addAddressReq);

  void deleteAddressesById(String id);

  Optional<AddressEntity> getAddressesById(String id);

  Iterable<AddressEntity> getAllAddresses();
}
