package com.example.ecomm.hateoas;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.ecomm.controller.OrderController;
import com.example.ecomm.entity.OrderEntity;
import com.example.ecomm.mapper.OrderMapper;
import com.example.ecomm.model.Order;
import com.example.ecomm.service.ItemService;

@Component
public class OrderRepresentationModelAssembler extends
    RepresentationModelAssemblerSupport<OrderEntity, Order> {

  private final OrderMapper orderMapper;
  private final UserRepresentationModelAssembler uAssembler;
  private final AddressRepresentationModelAssembler aAssembler;
  private final CardRepresentationModelAssembler cAssembler;
  private final ShipmentRepresentationModelAssembler sAssembler;
  private final PaymentRepresentationModelAssembler pAssembler;
  private final ItemService itemService;

  public OrderRepresentationModelAssembler(OrderMapper orderMapper, UserRepresentationModelAssembler uAssembler,
      AddressRepresentationModelAssembler aAssembler, CardRepresentationModelAssembler cAssembler,
      ShipmentRepresentationModelAssembler sAssembler, PaymentRepresentationModelAssembler pAssembler,
      ItemService itemService) {
    super(OrderController.class, Order.class);
    this.orderMapper = orderMapper;
    this.uAssembler = uAssembler;
    this.aAssembler = aAssembler;
    this.cAssembler = cAssembler;
    this.sAssembler = sAssembler;
    this.pAssembler = pAssembler;
    this.itemService = itemService;
  }

  @Override
  public Order toModel(OrderEntity entity) {
    Order resource = orderMapper.toModel(entity);

    resource
        .id(entity.getId().toString())
        .customer(uAssembler.toModel(entity.getUserEntity()))
        .address(aAssembler.toModel(entity.getAddressEntity()))
        .card(cAssembler.toModel(entity.getCardEntity()))
        .items(itemService.toModelList(entity.getItems()))
        .date(entity.getOrderDate().toInstant().atOffset(ZoneOffset.UTC))
        .total(entity.getTotal().doubleValue());

    if (entity.getShipment() != null) {
      resource.shipment(sAssembler.toModel(entity.getShipment()));
    }
    if (entity.getPaymentEntity() != null) {
      resource.payment(pAssembler.toModel(entity.getPaymentEntity()));
    }

    resource.add(linkTo(methodOn(OrderController.class).getByOrderId(entity.getId().toString())).withSelfRel());

    return resource;
  }

  public List<Order> toListModel(Iterable<OrderEntity> entities) {
    if (Objects.isNull(entities)) {
      return List.of();
    }

    return StreamSupport.stream(entities.spliterator(), false).map(this::toModel)
        .collect(toList());
  }
}
