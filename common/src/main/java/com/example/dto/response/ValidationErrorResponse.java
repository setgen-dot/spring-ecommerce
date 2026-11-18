package com.example.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
public class ValidationErrorResponse {

    private final String code = "VALIDATION_ERROR";

    private final List<FieldError> errors;

    private final Instant timestamp;

    public ValidationErrorResponse(List<FieldError> errors) {
        this.errors = errors;
        this.timestamp = Instant.now();
    }

    @Getter
    @AllArgsConstructor
    public static class FieldError {
        private String field;
        private String message;
    }
}
