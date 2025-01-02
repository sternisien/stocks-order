package register_order.unit_test;

import org.example.adapter.out.client.PortfolioHttpClient;
import org.example.adapter.out.producer.AppProducer;
import org.example.application.dto.CreateOrderDto;
import org.example.application.dto.kafka.LockAssetPortfolio;
import org.example.application.mapper.StockOrderMapper;
import org.example.application.service.RegisterOrderService;
import org.example.domain.enums.OrderSide;
import org.example.domain.enums.OrderType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RegisterOrderServiceTest {

  @Spy private StockOrderMapper stockOrderMapper = Mappers.getMapper(StockOrderMapper.class);

  @Mock private PortfolioHttpClient portfolioHttpClient;

  @Mock private AppProducer<LockAssetPortfolio> lockAssetPortfolioAppProducer;

  @InjectMocks private RegisterOrderService registerOrderService;

  @Test
  void register_order_ko_order_null() {

    Assertions.assertThrows(
        NullPointerException.class, () -> registerOrderService.registerOrder(null, OrderSide.BUY));
  }

  @Test
  void register_order_ko_order_side_null() {

    Assertions.assertThrows(
        NullPointerException.class,
        () ->
            registerOrderService.registerOrder(
                new CreateOrderDto("AAPL", 100, 25.5d, OrderType.MARKET, 1L), null));
  }

  @Test
  void register_order_sell_ko_quantity_not_available_0() {

    Assertions.assertThrows(
        NullPointerException.class,
        () ->
            registerOrderService.registerOrder(
                new CreateOrderDto("AAPL", 100, 25.5d, OrderType.MARKET, 1L), null));
  }
}
