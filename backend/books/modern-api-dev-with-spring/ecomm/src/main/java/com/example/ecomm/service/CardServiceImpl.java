package com.example.ecomm.service;

import java.util.Optional;
import java.util.UUID;
import org.springframework.validation.annotation.Validated;

import org.springframework.stereotype.Service;

import com.example.ecomm.entity.CardEntity;
import com.example.ecomm.entity.UserEntity;
import com.example.ecomm.model.AddCardReq;
import com.example.ecomm.repository.CardRepository;
import com.example.ecomm.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

  private final CardRepository repository;
  private final UserRepository userRepo;

  @Override
  public void deleteCardById(String id) {
    repository.deleteById(UUID.fromString(id));
  }

  @Override
  public Iterable<CardEntity> getAllCards() {
    return repository.findAll();
  }

  @Override
  public Optional<CardEntity> getCardById(String id) {
    return repository.findById(UUID.fromString(id));
  }

  @Override
  public Optional<CardEntity> registerCard(AddCardReq addCardReq) {
    return Optional.of(repository.save(toEntity(addCardReq)));
  }

  private CardEntity toEntity(AddCardReq m) {
    CardEntity e = new CardEntity();
    Optional<UserEntity> user = userRepo.findById(UUID.fromString(m.getUserId()));

    user.ifPresent(e::setUser);

    // Only the last 4 digits are retained; the CVV must never be persisted (PCI-DSS).
    return e.setNumber(lastFourDigits(m.getCardNumber())).setExpires(m.getExpires());
  }

  private static String lastFourDigits(String cardNumber) {
    if (cardNumber == null || cardNumber.length() <= 4) {
      return cardNumber;
    }

    return cardNumber.substring(cardNumber.length() - 4);
  }
}
