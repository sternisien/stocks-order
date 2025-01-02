package org.example.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Map;
import java.util.Objects;
import org.example.adapter.out.client.dto.BalancePortfolioDto;
import org.example.adapter.out.client.dto.StockPortfolioDto;
import org.example.application.dto.CreateOrderDto;
import org.example.application.dto.kafka.LockAssetPortfolio;
import org.example.application.mapper.StockOrderMapper;
import org.example.application.port.in.RegisterOrderUseCase;
import org.example.application.port.out.PortfolioClientRequest;
import org.example.application.port.out.ProducerMessage;
import org.example.application.port.out.StockOrderPersistence;
import org.example.domain.StockOrder;
import org.example.domain.enums.OrderSide;
import org.example.infrastructure.configuration.controller_advice.common.message.StockOrderMessageException;
import org.example.infrastructure.configuration.enums.KafkaTopic;
import org.example.infrastructure.exception.ForbiddenOperationException;
import org.example.infrastructure.exception.InternalException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class RegisterOrderService implements RegisterOrderUseCase {

  private final PortfolioClientRequest portfolioClientRequest;

  private final ProducerMessage<LockAssetPortfolio> producerLockerAsset;
  private final StockOrderMapper stockOrderMapper;
  private final StockOrderPersistence stockOrderPersistenceAdapter;

  public RegisterOrderService(
      PortfolioClientRequest portfolioClientRequest,
      ProducerMessage<LockAssetPortfolio> producerLockerAsset,
      StockOrderMapper stockOrderMapper,
      StockOrderPersistence stockOrderPersistenceAdapter) {
    this.portfolioClientRequest = portfolioClientRequest;
    this.producerLockerAsset = producerLockerAsset;
    this.stockOrderMapper = stockOrderMapper;
    this.stockOrderPersistenceAdapter = stockOrderPersistenceAdapter;
  }

  @Override
  @Transactional
  public Long registerOrder(CreateOrderDto createOrderDto, OrderSide orderSide) {
    Objects.requireNonNull(createOrderDto);
    Objects.requireNonNull(orderSide);

    return orderSide == OrderSide.BUY
        ? buyOrder(createOrderDto, orderSide)
        : sellOrder(createOrderDto, orderSide);
  }

  @Override
  @Transactional(propagation = Propagation.NEVER)
  public void emitLockAssetPortfolio(
      Long orderId, CreateOrderDto createOrderDto, OrderSide orderSide)
      throws JsonProcessingException {
    Objects.requireNonNull(orderId);
    Objects.requireNonNull(createOrderDto);
    Objects.requireNonNull(orderSide);

    Number stockQuantityOrAmountToLock =
        orderSide == OrderSide.SELL
            ? createOrderDto.quantity()
            : createOrderDto.quantity() * createOrderDto.price();
    LockAssetPortfolio lockAssetPortfolio =
        new LockAssetPortfolio(orderId, createOrderDto.userId(), stockQuantityOrAmountToLock);
    producerLockerAsset.produce(
        lockAssetPortfolio,
        Map.of(OrderSide.class.getSimpleName(), orderSide.name()),
        KafkaTopic.LOCK_ASSET_PORTFOLIO);
  }

  private Long sellOrder(CreateOrderDto createOrderDto, OrderSide orderSide) {
    try {
      StockPortfolioDto stockPortfolio =
          portfolioClientRequest.getStockBySymbolInUserPortfolio(
              createOrderDto.userId(), createOrderDto.symbol());
      int quantityForSellAsked = createOrderDto.quantity();
      int stockQuantityAvailable = stockPortfolio.quantity() - stockPortfolio.quantityLock();

      if (quantityForSellAsked > stockQuantityAvailable) {
        throw new ForbiddenOperationException(
            String.format(
                StockOrderMessageException.QUANTITY_AVAILABLE_INSUFFICIENT,
                createOrderDto.symbol(),
                stockQuantityAvailable),
            StockOrder.class.getSimpleName());
      }

      StockOrder stockOrder = stockOrderMapper.dtoToDomain(createOrderDto, orderSide);
      return stockOrderPersistenceAdapter.createOrder(stockOrder);
    } catch (RestClientException ex) {
      throw new RuntimeException();
    }
  }

  private Long buyOrder(CreateOrderDto createOrderDto, OrderSide orderSide) {
    try {
      BalancePortfolioDto balancePortfolioDto =
          portfolioClientRequest.getBalanceUserPortfolio(createOrderDto.userId());

      double amountAvailableWithFundsLocked =
          balancePortfolioDto.amountFundsAvailable() - balancePortfolioDto.amountFundsLocked();
      double amountStockForQuantity = createOrderDto.price() * createOrderDto.quantity();
      if (amountAvailableWithFundsLocked < amountStockForQuantity) {
        throw new ForbiddenOperationException("", StockOrder.class.getSimpleName());
      }

      StockOrder stockOrder = stockOrderMapper.dtoToDomain(createOrderDto, orderSide);
      return stockOrderPersistenceAdapter.createOrder(stockOrder);
    } catch (RestClientException ex) {
      throw new InternalException("", RestTemplate.class.getSimpleName());
    }
  }
}
