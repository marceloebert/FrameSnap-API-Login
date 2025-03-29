package com.fiap.framesnap.crosscutting.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class ErrorHandlingControllerAdviceTest {

    private final ErrorHandlingControllerAdvice advice = new ErrorHandlingControllerAdvice();

    @Test
    void shouldHandleNotFoundException() {
        // Arrange
        String errorMessage = "Resource not found";
        NotFoundException exception = new NotFoundException(errorMessage);

        // Act
        ResponseEntity<ValidationErrorResponse> response = ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(advice.notFoundException(exception));

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ValidationErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals(1, errorResponse.getViolations().size());
        assertEquals(errorMessage, errorResponse.getViolations().get(0).getMessage());
    }

    @Test
    void shouldHandleNotFoundExceptionWithDefaultMessage() {
        // Arrange
        NotFoundException exception = new NotFoundException();

        // Act
        ResponseEntity<ValidationErrorResponse> response = ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(advice.notFoundException(exception));

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ValidationErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals(1, errorResponse.getViolations().size());
        assertEquals("Not found", errorResponse.getViolations().get(0).getMessage());
    }
} 