package org.example.adapter.in;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.example.application.dto.CreateOrderDto;
import org.example.application.port.in.RegisterOrderUseCase;
import org.example.domain.enums.OrderSide;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/stock-orders")
public class OrderController {

  private final RegisterOrderUseCase registerOrderUseCase;

  public OrderController(RegisterOrderUseCase registerOrderUseCase) {
    this.registerOrderUseCase = registerOrderUseCase;
  }

  @PostMapping
  public ResponseEntity<Void> createStockOrder(
      @Valid @RequestBody CreateOrderDto createOrderDto, @RequestParam OrderSide orderSide)
      throws JsonProcessingException {
    Long resourceIdCreated = 1L; // registerOrderUseCase.registerOrder(createOrderDto, orderSide);
    registerOrderUseCase.emitLockAssetPortfolio(resourceIdCreated, createOrderDto, orderSide);
    return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(resourceIdCreated)
                .toUri())
        .build();
  }
}
