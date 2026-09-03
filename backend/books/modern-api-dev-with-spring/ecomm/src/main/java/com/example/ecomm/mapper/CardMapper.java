package com.example.ecomm.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.example.ecomm.entity.CardEntity;
import com.example.ecomm.model.Card;

@Mapper(componentModel = "spring")
public interface CardMapper {

  @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
  @Mapping(target = "cardNumber", source = "number")
  @Mapping(target = "userId", source = "user.id")
  @Mapping(target = "cvv", ignore = true)
  Card toModel(CardEntity entity);
}
