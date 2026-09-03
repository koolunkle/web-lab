package com.example.ecomm.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecomm.entity.AddressEntity;
import com.example.ecomm.entity.CardEntity;
import com.example.ecomm.entity.CartEntity;
import com.example.ecomm.entity.ItemEntity;
import com.example.ecomm.entity.OrderEntity;
import com.example.ecomm.entity.UserEntity;
import com.example.ecomm.exception.ResourceNotFoundException;
import com.example.ecomm.model.NewOrder;
import com.example.ecomm.model.Order.StatusEnum;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
@Transactional
public class OrderRepositoryImpl implements OrderRepositoryExt {

        @PersistenceContext
        private EntityManager em;

        private final ItemRepository itemRepo;
        private final CartRepository cartRepo;

        @Override
        public Optional<OrderEntity> insert(NewOrder m) {
                Iterable<ItemEntity> dbItems = itemRepo.findByCustomerId(m.getCustomerId());
                List<ItemEntity> items = StreamSupport.stream(dbItems.spliterator(), false).toList();

                if (items.isEmpty()) {
                        throw new ResourceNotFoundException(String.format(
                                        "There is no item found in customer's (ID: %s) cart.", m.getCustomerId()));
                }

                BigDecimal total = items.stream()
                                .map(i -> BigDecimal.valueOf(i.getQuantity()).multiply(i.getPrice()))
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                // em.find() is used instead of em.getReference() so the returned entity carries
                // fully-initialized associations rather than lazy proxies that would become
                // unusable once this method's transaction/session closes (open-in-view is disabled).
                OrderEntity entity = new OrderEntity();
                entity.setUserEntity(em.find(UserEntity.class, UUID.fromString(m.getCustomerId())));
                entity.setAddressEntity(em.find(AddressEntity.class, UUID.fromString(m.getAddress().getId())));
                entity.setCardEntity(em.find(CardEntity.class, UUID.fromString(m.getCard().getId())));
                entity.setOrderDate(Timestamp.from(Instant.now()));
                entity.setTotal(total);
                entity.setStatus(StatusEnum.CREATED);
                // Setting items here (rather than leaving them for a caller to populate) both
                // persists the order_item join rows via cascade and lets the returned entity
                // reflect its real items instead of the empty list a fresh OrderEntity starts with.
                entity.setItems(items);

                em.persist(entity);

                Optional<CartEntity> oCart = cartRepo.findByCustomerId(UUID.fromString(m.getCustomerId()));
                CartEntity cart = oCart.orElseThrow(() -> new ResourceNotFoundException(
                                String.format("Cart not found for given customer (ID: %s)", m.getCustomerId())));

                itemRepo.deleteCartItemJoinById(cart.getItems().stream().map(ItemEntity::getId).toList(), cart.getId());

                return Optional.of(entity);
        }
}
