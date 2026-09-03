package com.example.ecomm.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.example.ecomm.entity.ProductEntity;
import com.example.ecomm.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
  @Mapping(target = "tag", ignore = true)
  Product toModel(ProductEntity entity);
}
