package org.example.adapter.out;

import jakarta.transaction.Transactional;
import org.example.adapter.out.entity.StockOrderEntity;
import org.example.adapter.out.repository.StockOrderRepository;
import org.example.application.mapper.StockOrderMapper;
import org.example.application.port.out.StockOrderPersistence;
import org.example.domain.StockOrder;
import org.springframework.stereotype.Repository;

@Repository
public class StockOrderPersistenceAdapter implements StockOrderPersistence {

  private final StockOrderMapper stockOrderMapper;
  private final StockOrderRepository stockOrderRepository;

  public StockOrderPersistenceAdapter(
      StockOrderMapper stockOrderMapper, StockOrderRepository stockOrderRepository) {
    this.stockOrderMapper = stockOrderMapper;
    this.stockOrderRepository = stockOrderRepository;
  }

  @Override
  @Transactional
  public Long createOrder(StockOrder stockOrder) {
    StockOrderEntity orderToCreate = stockOrderMapper.domainToEntity(stockOrder);
    orderToCreate = stockOrderRepository.save(orderToCreate);

    return orderToCreate.getId();
  }
}
