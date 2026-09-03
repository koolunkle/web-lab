package com.example.ecomm.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.example.ecomm.entity.AddressEntity;
import com.example.ecomm.model.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {

  @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
  Address toModel(AddressEntity entity);
}
