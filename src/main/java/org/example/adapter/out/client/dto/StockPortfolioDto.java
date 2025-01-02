package org.example.adapter.out.client.dto;

import java.time.LocalDateTime;

public record StockPortfolioDto(
    String symbol, int quantity, double price, int quantityLock, LocalDateTime lastUpdated) {}
