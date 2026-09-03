package com.example.ecomm.hateoas;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.ecomm.controller.ShipmentController;
import com.example.ecomm.entity.ShipmentEntity;
import com.example.ecomm.mapper.ShipmentMapper;
import com.example.ecomm.model.Shipment;

@Component
public class ShipmentRepresentationModelAssembler extends
    RepresentationModelAssemblerSupport<ShipmentEntity, Shipment> {

  private final ShipmentMapper shipmentMapper;

  public ShipmentRepresentationModelAssembler(ShipmentMapper shipmentMapper) {
    super(ShipmentController.class, Shipment.class);
    this.shipmentMapper = shipmentMapper;
  }

  @Override
  public Shipment toModel(ShipmentEntity entity) {
    Shipment resource = shipmentMapper.toModel(entity);

    resource.add(linkTo(methodOn(ShipmentController.class).getShipmentByOrderId(entity.getId().toString()))
        .withRel("byOrderId"));

    return resource;
  }

  public List<Shipment> toListModel(Iterable<ShipmentEntity> entities) {
    if (Objects.isNull(entities)) {
      return List.of();
    }

    return StreamSupport.stream(entities.spliterator(), false).map(this::toModel).collect(toList());
  }
}
