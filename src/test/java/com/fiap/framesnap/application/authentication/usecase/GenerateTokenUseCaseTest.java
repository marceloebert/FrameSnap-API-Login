package com.fiap.framesnap.application.authentication.usecase;

import com.fiap.framesnap.application.authentication.gateways.TokenGateway;
import com.fiap.framesnap.application.authentication.usecases.GenerateTokenUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class GenerateTokenUseCaseTest {

    @Mock
    private TokenGateway tokenGateway;

    private GenerateTokenUseCase generateTokenUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        generateTokenUseCase = new GenerateTokenUseCase(tokenGateway);
    }

    @Test
    void shouldGenerateToken() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        String expectedToken = "test-token";
        when(tokenGateway.generateToken(email, password)).thenReturn(expectedToken);

        // Act
        String token = generateTokenUseCase.execute(email, password);

        // Assert
        assertNotNull(token);
        assertEquals(expectedToken, token);
    }

    @Test
    void shouldThrowExceptionForInvalidCredentials() {
        // Arrange
        String email = "test@example.com";
        String password = "wrong-password";
        when(tokenGateway.generateToken(anyString(), anyString()))
                .thenThrow(new RuntimeException("Invalid credentials"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            generateTokenUseCase.execute(email, password);
        });
    }
} 