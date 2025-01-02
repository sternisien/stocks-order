package org.example.application.port.in;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.application.dto.CreateOrderDto;
import org.example.domain.enums.OrderSide;

public interface RegisterOrderUseCase {

  Long registerOrder(CreateOrderDto createOrderDto, OrderSide orderSide);

  void emitLockAssetPortfolio(Long orderId, CreateOrderDto createOrderDto, OrderSide orderSide)
      throws JsonProcessingException;
}
