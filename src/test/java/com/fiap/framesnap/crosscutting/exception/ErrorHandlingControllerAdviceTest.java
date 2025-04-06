package com.fiap.framesnap.crosscutting.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ErrorHandlingControllerAdviceTest {

    private ErrorHandlingControllerAdvice advice;
    private WebRequest webRequest;

    @BeforeEach
    void setUp() {
        advice = new ErrorHandlingControllerAdvice();
        webRequest = mock(WebRequest.class);
        when(webRequest.getDescription(false)).thenReturn("uri=/test");
    }

    @Test
    void shouldHandleNotFoundException() {
        // Arrange
        String errorMessage = "Recurso não encontrado";
        NotFoundException exception = new NotFoundException(errorMessage);

        // Act
        ValidationErrorResponse response = advice.notFoundException(exception, webRequest);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getViolations());
        assertEquals(1, response.getViolations().size());
        assertEquals(errorMessage, response.getViolations().get(0).getMessage());
    }

    @Test
    void shouldHandleNotFoundExceptionWithDefaultMessage() {
        // Arrange
        NotFoundException exception = new NotFoundException();

        // Act
        ValidationErrorResponse response = advice.notFoundException(exception, webRequest);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getViolations());
        assertEquals(1, response.getViolations().size());
        assertEquals("Not found", response.getViolations().get(0).getMessage());
    }

    @Test
    void shouldHandleAllExceptions() {
        // Arrange
        String errorMessage = "Erro interno";
        Exception exception = new RuntimeException(errorMessage);

        // Act
        ErrorResponse response = advice.handleAllExceptions(exception, webRequest);

        // Assert
        assertNotNull(response);
        assertEquals(errorMessage, response.getMessage());
        assertEquals(500, response.getStatus());
        assertEquals("Internal Server Error", response.getError());
        assertEquals("uri=/test", response.getPath());
        assertTrue(response.getTimestamp() > 0);
    }
} 