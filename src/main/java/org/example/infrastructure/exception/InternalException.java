package org.example.infrastructure.exception;

import java.time.LocalDateTime;

/** Exception pour les resources non trouvées */
public class InternalException extends ApiException {
  public InternalException(String message, String resourceType) {
    super(message, resourceType, LocalDateTime.now());
  }
}
