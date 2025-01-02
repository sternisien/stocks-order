package org.example.infrastructure.exception;

import java.time.LocalDateTime;

/** Exception pour les resources non trouvées */
public class ForbiddenOperationException extends ApiException {
  public ForbiddenOperationException(String message, String resourceType) {
    super(message, resourceType, LocalDateTime.now());
  }
}
