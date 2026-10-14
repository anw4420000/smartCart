package com.smartcart.smartcart_api.common;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

public class GlobalExceptionHandler {
     @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(
            MethodArgumentNotValidException exception) {

        Map<String, String> errors = exception.getBindingResult()
            .getFieldErrors()
            .stream()
            .collect(Collectors.toMap(
                error -> error.getField(),
                error -> error.getDefaultMessage() == null
                    ? "Invalid value"
                    : error.getDefaultMessage(),
                (first, second) -> first
            ));

        return ResponseEntity.badRequest().body(
            new ApiError(
                Instant.now(),
                400,
                "Bad Request",
                "Please correct the invalid fields",
                errors
            )
        );
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiError> handleStatus(
            ResponseStatusException exception) {

        HttpStatus status = HttpStatus.valueOf(
            exception.getStatusCode().value()
        );

        return ResponseEntity.status(status).body(
            new ApiError(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                exception.getReason() == null
                    ? "Request failed"
                    : exception.getReason(),
                Map.of()
            )
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrity(
            DataIntegrityViolationException exception) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(
            new ApiError(
                Instant.now(),
                409,
                "Conflict",
                "The operation conflicts with existing data",
                Map.of()
            )
        );
    }
}
