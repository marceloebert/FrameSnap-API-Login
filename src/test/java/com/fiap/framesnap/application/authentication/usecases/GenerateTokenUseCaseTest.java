package com.fiap.framesnap.application.authentication.usecases;

import com.fiap.framesnap.application.authentication.gateways.TokenGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GenerateTokenUseCaseTest {

    private GenerateTokenUseCase generateTokenUseCase;

    @Mock
    private TokenGateway tokenGateway;

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
        verify(tokenGateway).generateToken(email, password);
    }

    @Test
    void shouldReturnNullForInvalidCredentials() {
        // Arrange
        String email = "test@example.com";
        String password = "wrong-password";

        when(tokenGateway.generateToken(email, password)).thenReturn(null);

        // Act
        String token = generateTokenUseCase.execute(email, password);

        // Assert
        assertNull(token);
        verify(tokenGateway).generateToken(email, password);
    }





    @Test
    void shouldHandleEmptyEmail() {
        // Arrange
        String email = "";
        String password = "password123";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            generateTokenUseCase.execute(email, password);
        });
        verify(tokenGateway, never()).generateToken(any(), any());
    }

    @Test
    void shouldHandleEmptyPassword() {
        // Arrange
        String email = "test@example.com";
        String password = "";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            generateTokenUseCase.execute(email, password);
        });
        verify(tokenGateway, never()).generateToken(any(), any());
    }
} 