package org.example.application.dto.kafka;

/**
 * Kafka DTO permettant d'envoyer un message au module portfolio afin de verrouiller un nombre
 * d'actions du portefeuille d'un utilisateur lorsqu'un {@link
 * org.example.domain.enums.OrderSide#SELL} est placé
 */
public record LockAssetPortfolio(Long orderId, Long userId, Number stockQuantityOrAmount) {}
