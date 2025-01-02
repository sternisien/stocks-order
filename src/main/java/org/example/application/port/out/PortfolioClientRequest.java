package org.example.application.port.out;

import org.example.adapter.out.client.dto.BalancePortfolioDto;
import org.example.adapter.out.client.dto.StockPortfolioDto;

public interface PortfolioClientRequest {

  StockPortfolioDto getStockBySymbolInUserPortfolio(Long userId, String symbol);

  BalancePortfolioDto getBalanceUserPortfolio(Long userId);
}
