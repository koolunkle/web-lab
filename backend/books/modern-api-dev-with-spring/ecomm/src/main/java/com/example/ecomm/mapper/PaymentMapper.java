package com.example.ecomm.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.example.ecomm.entity.PaymentEntity;
import com.example.ecomm.model.Payment;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

  @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
  Payment toModel(PaymentEntity entity);
}
