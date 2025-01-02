package org.example.application.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.example.domain.enums.OrderType;

public record CreateOrderDto(
    @NotNull @NotEmpty String symbol,
    @Min(1) int quantity,
    @DecimalMin("0.1") double price,
    @NotNull OrderType orderType,
    @NotNull Long userId) {}
