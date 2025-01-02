package org.example.infrastructure.configuration.controller_advice.common;

import java.time.LocalDateTime;

public record ApiError(
    String message,
    int httpStatusCode,
    String httpStatus,
    String resourceType,
    LocalDateTime date) {}
