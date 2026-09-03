package com.example.ecomm.hateoas;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.ecomm.controller.PaymentController;
import com.example.ecomm.entity.PaymentEntity;
import com.example.ecomm.mapper.PaymentMapper;
import com.example.ecomm.model.Payment;

@Component
public class PaymentRepresentationModelAssembler extends
    RepresentationModelAssemblerSupport<PaymentEntity, Payment> {

  private final PaymentMapper paymentMapper;

  public PaymentRepresentationModelAssembler(PaymentMapper paymentMapper) {
    super(PaymentController.class, Payment.class);
    this.paymentMapper = paymentMapper;
  }

  @Override
  public Payment toModel(PaymentEntity entity) {
    Payment resource = paymentMapper.toModel(entity);

    resource.add(linkTo(methodOn(PaymentController.class).getOrdersPaymentAuthorization(entity.getId().toString()))
        .withSelfRel());

    return resource;
  }

  public List<Payment> toListModel(Iterable<PaymentEntity> entities) {
    if (Objects.isNull(entities)) {
      return List.of();
    }

    return StreamSupport.stream(entities.spliterator(), false).map(this::toModel)
        .collect(toList());
  }
}
