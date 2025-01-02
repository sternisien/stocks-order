package org.example.infrastructure.exception;

import java.time.LocalDateTime;

/** Exception pour les resources non trouvées */
public class ResourceNotFoundException extends ApiException {
  public ResourceNotFoundException(String message, String resourceType) {
    super(message, resourceType, LocalDateTime.now());
  }
}
