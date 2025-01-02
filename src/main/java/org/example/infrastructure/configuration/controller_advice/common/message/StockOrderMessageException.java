package org.example.infrastructure.configuration.controller_advice.common.message;

public class StockOrderMessageException {

  public static final String QUANTITY_AVAILABLE_INSUFFICIENT =
      "Impossible de placer un ordre sur l'action %s. Quantité Max Disponible : %s";

  private StockOrderMessageException() {}
}
