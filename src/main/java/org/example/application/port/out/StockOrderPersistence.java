package org.example.application.port.out;

import org.example.domain.StockOrder;

public interface StockOrderPersistence {

  Long createOrder(StockOrder stockOrder);
}
