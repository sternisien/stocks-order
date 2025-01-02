package org.example.application.mapper;

import java.time.LocalDateTime;
import org.example.adapter.out.entity.StockOrderEntity;
import org.example.application.dto.CreateOrderDto;
import org.example.domain.StockOrder;
import org.example.domain.enums.OrderSide;
import org.example.domain.enums.OrderStatus;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StockOrderMapper {

  StockOrder dtoToDomain(CreateOrderDto createOrderDto, OrderSide orderSide);

  StockOrderEntity domainToEntity(StockOrder stockOrder);

  @AfterMapping
  default void populateStockOrderCreated(
      @MappingTarget StockOrder stockOrder, OrderSide orderSide) {
    LocalDateTime now = LocalDateTime.now();
    stockOrder.setOrderSide(orderSide);
    stockOrder.setStatus(OrderStatus.PENDING);
    stockOrder.setCreatedAt(now);
    stockOrder.setUpdatedAt(now);
  }
}
