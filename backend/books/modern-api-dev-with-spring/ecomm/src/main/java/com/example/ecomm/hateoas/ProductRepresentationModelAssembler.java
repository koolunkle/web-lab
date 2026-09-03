package com.example.ecomm.hateoas;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.ecomm.controller.ProductController;
import com.example.ecomm.entity.ProductEntity;
import com.example.ecomm.mapper.ProductMapper;
import com.example.ecomm.model.Product;
import com.example.ecomm.model.Tag;

@Component
public class ProductRepresentationModelAssembler extends
    RepresentationModelAssemblerSupport<ProductEntity, Product> {

  private final ProductMapper productMapper;

  public ProductRepresentationModelAssembler(ProductMapper productMapper) {
    super(ProductController.class, Product.class);
    this.productMapper = productMapper;
  }

  @Override
  public Product toModel(ProductEntity entity) {
    Product resource = productMapper.toModel(entity);

    resource.setTag(
        entity.getTags().stream().map(t -> new Tag().id(t.getId().toString()).name(t.getName())).collect(toSet()));

    resource.add(linkTo(methodOn(ProductController.class).getProduct(entity.getId().toString())).withSelfRel());
    resource.add(linkTo(methodOn(ProductController.class).queryProducts(null, null, 1, 10)).withRel("products"));

    return resource;
  }

  public List<Product> toListModel(Iterable<ProductEntity> entities) {
    if (Objects.isNull(entities)) {
      return List.of();
    }
    
    return StreamSupport.stream(entities.spliterator(), false).map(this::toModel)
        .collect(toList());
  }
}
