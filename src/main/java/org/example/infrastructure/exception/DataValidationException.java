package org.example.infrastructure.exception;

import java.time.LocalDateTime;

/** Exception pour les resources non trouvées */
public class DataValidationException extends ApiException {
  public DataValidationException(String message, String resourceType) {
    super(message, resourceType, LocalDateTime.now());
  }
}
