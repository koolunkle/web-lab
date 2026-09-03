package com.example.ecomm.hateoas;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.ecomm.controller.AddressController;
import com.example.ecomm.entity.AddressEntity;
import com.example.ecomm.mapper.AddressMapper;
import com.example.ecomm.model.Address;

@Component
public class AddressRepresentationModelAssembler extends
    RepresentationModelAssemblerSupport<AddressEntity, Address> {

  private final AddressMapper addressMapper;

  public AddressRepresentationModelAssembler(AddressMapper addressMapper) {
    super(AddressController.class, Address.class);
    this.addressMapper = addressMapper;
  }

  @Override
  public Address toModel(AddressEntity entity) {
    Address resource = addressMapper.toModel(entity);

    resource.add(linkTo(methodOn(AddressController.class).getAddressesById(entity.getId().toString())).withSelfRel());

    return resource;
  }

  public List<Address> toListModel(Iterable<AddressEntity> entities) {
    if (Objects.isNull(entities)) {
      return List.of();
    }

    return StreamSupport.stream(entities.spliterator(), false).map(this::toModel)
        .collect(toList());
  }
}
