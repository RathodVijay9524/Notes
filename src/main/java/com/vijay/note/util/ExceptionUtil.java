package com.vijay.note.util;


import com.vijay.note.exceptions.GenericResponse;
import com.vijay.note.exceptions.ErrorDetails;
import com.vijay.note.exceptions.ResourceNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Log4j2
public class ExceptionUtil {


    /**
     * Handles exceptions for fetching entities by ID.
     *
     * @param ex         the exception to handle
     * @param entityName the name of the entity (e.g., "Category", "Product")
     * @return a ResponseEntity with an appropriate error message and HTTP status
     */
    public static ResponseEntity<?> handleGetByIdExceptions(Throwable ex, String entityName) {
        log.error("Error fetching {}: {}", entityName, ex.getMessage());
        if (ex.getCause() instanceof ResourceNotFoundException) {
            return ExceptionUtil.createErrorResponseMessage(
                    String.format("%s not found", entityName),
                    HttpStatus.NOT_FOUND
            );
        }
        return ExceptionUtil.createErrorResponseMessage("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles exceptions for fetching entities by ID in a generic, reusable way.
     *
     * @param <T>        The response type expected by the controller.
     * @param ex         The exception encountered.
     * @param entityName The name of the entity (e.g., "Category", "Product").
     * @return A ResponseEntity with the appropriate status and error message.
     */
    public static <T> ResponseEntity<T> handleGetByIdException(Throwable ex, String entityName) {
        log.error("Error fetching {}: {}", entityName, ex.getMessage());
        if (ex.getCause() instanceof ResourceNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Type-safe 404 response
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Type-safe 500 response
    }


    // Generic success response
    public static <T> ResponseEntity<T> createGenericResponse(T body, HttpStatus status) {
        return new ResponseEntity<>(body, status);
    }

    // Success response with data
    public static ResponseEntity<?> createBuildResponse(Object data, HttpStatus status) {
        GenericResponse response = GenericResponse.builder()
                .responseStatus(status)
                .status("success")
                .message("success")
                .data(data)
                .build();
        return createGenericResponse(response, status);
    }

    // Success response with a custom message
    public static ResponseEntity<?> createBuildResponseMessage(String message, HttpStatus status) {
        GenericResponse response = GenericResponse.builder()
                .responseStatus(status)
                .status("success")
                .message(message)
                .build();
        return createGenericResponse(response, status);
    }

    // Error response with details
    public static ResponseEntity<?> createErrorResponse(Object details, HttpStatus status) {
        ErrorDetails response = ErrorDetails.builder()
                .responseStatus(status)
                .status("failed")
                .errorMessage("An error occurred..!!")
                .details(details)
                .timestamp(LocalDateTime.now())
                .build();
        return createGenericResponse(response, status);
    }

    // Error response with a custom error message
    public static ResponseEntity<?> createErrorResponseMessage(String errorMessage, HttpStatus status) {
        ErrorDetails response = ErrorDetails.builder()
                .responseStatus(status)
                .status("failed")
                .errorMessage(errorMessage)
                .timestamp(LocalDateTime.now())
                .build();
        return createGenericResponse(response, status);
    }
}

















