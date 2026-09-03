package com.example.ecomm.controller;

import static org.springframework.http.ResponseEntity.notFound;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecomm.CardApi;
import com.example.ecomm.hateoas.CardRepresentationModelAssembler;
import com.example.ecomm.model.AddCardReq;
import com.example.ecomm.model.Card;
import com.example.ecomm.service.CardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CardController implements CardApi {

  private final CardService service;
  private final CardRepresentationModelAssembler assembler;

  @Override
  public ResponseEntity<Void> deleteCardById(String id) {
    service.deleteCardById(id);
    return ResponseEntity.accepted().build();
  }

  @Override
  public ResponseEntity<List<Card>> getAllCards() {
    return ResponseEntity.ok(assembler.toListModel(service.getAllCards()));
  }

  @Override
  public ResponseEntity<Card> getCardById(String id) {
    return service.getCardById(id)
        .map(assembler::toModel)
        .map(ResponseEntity::ok)
        .orElse(notFound().build());
  }

  @Override
  public ResponseEntity<Card> registerCard(AddCardReq addCardReq) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(service.registerCard(addCardReq).map(assembler::toModel).get());
  }
}
