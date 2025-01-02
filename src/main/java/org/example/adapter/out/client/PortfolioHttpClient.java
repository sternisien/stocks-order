package org.example.adapter.out.client;

import org.example.adapter.out.client.dto.BalancePortfolioDto;
import org.example.adapter.out.client.dto.StockPortfolioDto;
import org.example.application.port.out.PortfolioClientRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class PortfolioHttpClient implements PortfolioClientRequest {

  private final RestTemplate restTemplate;

  @Value("${http.client.portfolio.host}")
  private String host;

  @Value("${http.client.portfolio.port}")
  private Integer port;

  public PortfolioHttpClient(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public StockPortfolioDto getStockBySymbolInUserPortfolio(Long userId, String symbol)
      throws RestClientException {
    return restTemplate
        .getForEntity(
            String.format("http://%s:%d/portfolios/{userId}/stocks/{symbol}", host, port),
            StockPortfolioDto.class,
            userId,
            symbol)
        .getBody();
  }

  @Override
  public BalancePortfolioDto getBalanceUserPortfolio(Long userId) {
    return restTemplate
        .getForEntity(
            String.format("http://%s:%d/portfolios/{userId}/balance", host, port),
            BalancePortfolioDto.class,
            userId)
        .getBody();
  }
}
