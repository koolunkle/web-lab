package com.example.ecomm.service;

import java.util.Optional;

import com.example.ecomm.entity.AddressEntity;
import com.example.ecomm.entity.CardEntity;
import com.example.ecomm.entity.UserEntity;
import com.example.ecomm.model.RefreshToken;
import com.example.ecomm.model.SignedInUser;
import com.example.ecomm.model.User;

public interface UserService {

  void deleteCustomerById(String id);

  Optional<Iterable<AddressEntity>> getAddressesByCustomerId(String id);

  Iterable<UserEntity> getAllCustomers();

  Optional<CardEntity> getCardByCustomerId(String id);

  Optional<UserEntity> getCustomerById(String id);

  UserEntity findUserByUsername(String username);

  Optional<SignedInUser> createUser(User user);

  SignedInUser getSignedInUser(UserEntity userEntity);

  Optional<SignedInUser> getAccessToken(RefreshToken refreshToken);

  void removeRefreshToken(RefreshToken refreshToken);
}
