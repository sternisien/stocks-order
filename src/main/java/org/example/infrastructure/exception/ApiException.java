package org.example.infrastructure.exception;

import java.time.LocalDateTime;

public abstract class ApiException extends RuntimeException {

  protected final String resourceType;
  protected final LocalDateTime localDateTime;

  protected ApiException(String message, String resourceType, LocalDateTime localDateTime) {
    super(message);
    this.resourceType = resourceType;
    this.localDateTime = localDateTime;
  }

  public String getResourceType() {
    return resourceType;
  }

  public LocalDateTime getLocalDateTime() {
    return localDateTime;
  }
}
