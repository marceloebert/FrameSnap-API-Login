package com.fiap.framesnap.application.authentication.usecase;

import com.fiap.framesnap.application.authentication.gateways.TokenGateway;
import com.fiap.framesnap.application.authentication.usecases.ValidateTokenUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ValidateTokenUseCaseTest {

    @Mock
    private TokenGateway tokenGateway;

    private ValidateTokenUseCase validateTokenUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validateTokenUseCase = new ValidateTokenUseCase(tokenGateway);
    }

    @Test
    void shouldValidateToken() {
        // Arrange
        String token = "valid-token";
        when(tokenGateway.validateToken(token)).thenReturn(true);

        // Act
        boolean isValid = validateTokenUseCase.isValid(token);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void shouldReturnFalseForInvalidToken() {
        // Arrange
        String token = "invalid-token";
        when(tokenGateway.validateToken(token)).thenReturn(false);

        // Act
        boolean isValid = validateTokenUseCase.isValid(token);

        // Assert
        assertFalse(isValid);
    }

    @Test
    void shouldReturnFalseForNullToken() {
        // Arrange
        when(tokenGateway.validateToken(null)).thenReturn(false);

        // Act
        boolean isValid = validateTokenUseCase.isValid(null);

        // Assert
        assertFalse(isValid);
    }
} 