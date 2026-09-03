package com.example.ecomm.service;

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.ecomm.entity.ItemEntity;
import com.example.ecomm.entity.ProductEntity;
import com.example.ecomm.model.Item;

@Service
public class ItemServiceImpl implements ItemService {

  @Override
  public ItemEntity toEntity(Item m) {
    ItemEntity e = new ItemEntity();

    e.setProduct(new ProductEntity().setId(UUID.fromString(m.getId())))
        .setPrice(BigDecimal.valueOf(m.getUnitPrice()))
        .setQuantity(m.getQuantity());

    return e;
  }

  @Override
  public List<ItemEntity> toEntityList(List<Item> items) {
    if (Objects.isNull(items)) {
      return List.of();
    }

    return items.stream().map(this::toEntity).collect(toList());
  }

  @Override
  public Item toModel(ItemEntity e) {
    Item m = new Item();

    m.id(e.getProduct().getId().toString())
        .unitPrice(e.getPrice().doubleValue())
        .quantity(e.getQuantity());

    return m;
  }

  @Override
  public List<Item> toModelList(List<ItemEntity> items) {
    if (Objects.isNull(items)) {
      return List.of();
    }

    return items.stream().map(this::toModel).collect(toList());
  }
}
