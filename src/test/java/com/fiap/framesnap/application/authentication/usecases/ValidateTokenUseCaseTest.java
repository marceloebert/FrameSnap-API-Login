package com.fiap.framesnap.application.authentication.usecases;

import com.fiap.framesnap.application.authentication.gateways.TokenGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ValidateTokenUseCaseTest {

    private ValidateTokenUseCase validateTokenUseCase;

    @Mock
    private TokenGateway tokenGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validateTokenUseCase = new ValidateTokenUseCase(tokenGateway);
    }

    @Test
    void shouldReturnTrueForValidToken() {
        // Arrange
        String token = "valid.token.here";
        when(tokenGateway.validateToken(token)).thenReturn(true);

        // Act
        boolean isValid = validateTokenUseCase.isValid(token);

        // Assert
        assertTrue(isValid);
        verify(tokenGateway).validateToken(token);
    }

    @Test
    void shouldReturnFalseForInvalidToken() {
        // Arrange
        String token = "invalid.token.here";
        when(tokenGateway.validateToken(token)).thenReturn(false);

        // Act
        boolean isValid = validateTokenUseCase.isValid(token);

        // Assert
        assertFalse(isValid);
        verify(tokenGateway).validateToken(token);
    }

    @Test
    void shouldReturnFalseForNullToken() {
        // Arrange
        String token = null;
        when(tokenGateway.validateToken(token)).thenReturn(false);

        // Act
        boolean isValid = validateTokenUseCase.isValid(token);

        // Assert
        assertFalse(isValid);
        verify(tokenGateway).validateToken(token);
    }

    @Test
    void shouldReturnFalseForEmptyToken() {
        // Arrange
        String token = "";
        when(tokenGateway.validateToken(token)).thenReturn(false);

        // Act
        boolean isValid = validateTokenUseCase.isValid(token);

        // Assert
        assertFalse(isValid);
        verify(tokenGateway).validateToken(token);
    }
} 