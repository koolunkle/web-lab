package com.example.ecomm.hateoas;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.ecomm.controller.CustomerController;
import com.example.ecomm.entity.UserEntity;
import com.example.ecomm.mapper.UserMapper;
import com.example.ecomm.model.User;

@Component
public class UserRepresentationModelAssembler extends
    RepresentationModelAssemblerSupport<UserEntity, User> {

  private final UserMapper userMapper;

  public UserRepresentationModelAssembler(UserMapper userMapper) {
    super(CustomerController.class, User.class);
    this.userMapper = userMapper;
  }

  @Override
  public User toModel(UserEntity entity) {
    User resource = userMapper.toModel(entity);

    resource.add(linkTo(methodOn(CustomerController.class).getCustomerById(entity.getId().toString())).withSelfRel());
    resource.add(linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("customers"));
    resource.add(linkTo(methodOn(CustomerController.class).getAddressesByCustomerId(entity.getId().toString()))
        .withRel("self_addresses"));

    return resource;
  }

  public List<User> toListModel(Iterable<UserEntity> entities) {
    if (Objects.isNull(entities)) {
      return List.of();
    }

    return StreamSupport.stream(entities.spliterator(), false).map(this::toModel)
        .collect(toList());
  }
}
