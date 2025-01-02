package org.example.domain;

import java.time.LocalDateTime;
import org.example.domain.enums.OrderSide;
import org.example.domain.enums.OrderStatus;
import org.example.domain.enums.OrderType;

public class StockOrder {

  private Long id;

  private Long userId;

  private String symbol;

  private OrderSide orderSide;

  private OrderType orderType;

  private int quantity;

  private double price;

  private int quantityLock;

  private OrderStatus status;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  public StockOrder() {}

  public StockOrder(
      Long id,
      Long userId,
      String symbol,
      OrderSide orderSide,
      OrderType orderType,
      int quantity,
      double price,
      int quantityLock,
      OrderStatus status,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    this.id = id;
    this.userId = userId;
    this.symbol = symbol;
    this.orderSide = orderSide;
    this.orderType = orderType;
    this.quantity = quantity;
    this.price = price;
    this.quantityLock = quantityLock;
    this.status = status;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public OrderSide getOrderSide() {
    return orderSide;
  }

  public void setOrderSide(OrderSide orderSide) {
    this.orderSide = orderSide;
  }

  public OrderType getOrderType() {
    return orderType;
  }

  public void setOrderType(OrderType orderType) {
    this.orderType = orderType;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public double getprice() {
    return price;
  }

  public void setprice(double price) {
    this.price = price;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public void setStatus(OrderStatus status) {
    this.status = status;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getQuantityLock() {
    return quantityLock;
  }

  public void setQuantityLock(int quantityLock) {
    this.quantityLock = quantityLock;
  }
}
