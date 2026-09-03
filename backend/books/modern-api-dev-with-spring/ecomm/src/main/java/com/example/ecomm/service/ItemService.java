package com.example.ecomm.service;

import java.util.List;

import com.example.ecomm.entity.ItemEntity;
import com.example.ecomm.model.Item;

public interface ItemService {

  ItemEntity toEntity(Item m);

  List<ItemEntity> toEntityList(List<Item> items);

  Item toModel(ItemEntity e);

  List<Item> toModelList(List<ItemEntity> items);
}
