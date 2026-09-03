package com.example.ecomm.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.example.ecomm.entity.CardEntity;

public interface CardRepository extends CrudRepository<CardEntity, UUID> {
}
