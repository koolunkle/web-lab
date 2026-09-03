package com.example.ecomm.service;

import java.util.Optional;

import com.example.ecomm.entity.CardEntity;
import com.example.ecomm.model.AddCardReq;

import jakarta.validation.Valid;

public interface CardService {
  
  void deleteCardById(String id);

  Iterable<CardEntity> getAllCards();

  Optional<CardEntity> getCardById(String id);

  Optional<CardEntity> registerCard(@Valid AddCardReq addCardReq);
}
