package com.example.ecomm.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.example.ecomm.entity.UserEntity;
import com.example.ecomm.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
  User toModel(UserEntity entity);

  @Mapping(target = "id", ignore = true)
  @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
  UserEntity toEntity(User user);
}
