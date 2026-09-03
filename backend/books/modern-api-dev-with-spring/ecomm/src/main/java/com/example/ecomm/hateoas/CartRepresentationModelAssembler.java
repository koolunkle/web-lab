package com.example.ecomm.hateoas;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.ecomm.controller.CartsController;
import com.example.ecomm.entity.CartEntity;
import com.example.ecomm.mapper.CartMapper;
import com.example.ecomm.model.Cart;
import com.example.ecomm.service.ItemService;

@Component
public class CartRepresentationModelAssembler extends
    RepresentationModelAssemblerSupport<CartEntity, Cart> {

  private final CartMapper cartMapper;
  private final ItemService itemService;

  public CartRepresentationModelAssembler(CartMapper cartMapper, ItemService itemService) {
    super(CartsController.class, Cart.class);
    this.cartMapper = cartMapper;
    this.itemService = itemService;
  }

  @Override
  public Cart toModel(CartEntity entity) {
    Cart resource = cartMapper.toModel(entity);
    resource.setItems(itemService.toModelList(entity.getItems()));

    resource.add(linkTo(methodOn(CartsController.class).getCartByCustomerId(resource.getCustomerId())).withSelfRel());
    resource.add(linkTo(methodOn(CartsController.class).getCartItemsByCustomerId(resource.getCustomerId()))
        .withRel("cart-items"));

    return resource;
  }

  public List<Cart> toListModel(Iterable<CartEntity> entities) {
    if (Objects.isNull(entities)) {
      return List.of();
    }

    return StreamSupport.stream(entities.spliterator(), false).map(this::toModel)
        .collect(toList());
  }
}
