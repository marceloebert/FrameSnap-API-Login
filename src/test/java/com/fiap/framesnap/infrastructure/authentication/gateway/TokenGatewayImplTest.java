package com.fiap.framesnap.infrastructure.authentication.gateway;

import com.fiap.framesnap.application.authentication.gateways.TokenGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthFlowType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.InitiateAuthRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.InitiateAuthResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthenticationResultType;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TokenGatewayImplTest {

    @Mock
    private CognitoIdentityProviderClient cognitoClient;

    private TokenGatewayImpl tokenGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tokenGateway = new TokenGatewayImpl(cognitoClient);
        ReflectionTestUtils.setField(tokenGateway, "clientId", "test-client-id");
        ReflectionTestUtils.setField(tokenGateway, "clientSecret", "test-client-secret");
    }

    @Test
    void shouldGenerateToken() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        String expectedToken = "test-token";

        InitiateAuthResponse authResponse = InitiateAuthResponse.builder()
                .authenticationResult(AuthenticationResultType.builder()
                        .idToken(expectedToken)
                        .build())
                .build();

        when(cognitoClient.initiateAuth(any(InitiateAuthRequest.class))).thenReturn(authResponse);

        // Act
        String token = tokenGateway.generateToken(email, password);

        // Assert
        assertNotNull(token);
        assertEquals(expectedToken, token);
    }

    @Test
    void shouldThrowExceptionForInvalidCredentials() {
        // Arrange
        String email = "test@example.com";
        String password = "wrong-password";

        when(cognitoClient.initiateAuth(any(InitiateAuthRequest.class)))
                .thenThrow(new RuntimeException("Invalid credentials"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            tokenGateway.generateToken(email, password);
        });
    }

    @Test
    void shouldValidateToken() {
        // Arrange
        String token = "valid-token";

        // Act
        boolean isValid = tokenGateway.validateToken(token);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void shouldReturnFalseForInvalidToken() {
        // Arrange
        String token = "";

        // Act
        boolean isValid = tokenGateway.validateToken(token);

        // Assert
        assertFalse(isValid);
    }

    @Test
    void shouldReturnFalseForNullToken() {
        // Act
        boolean isValid = tokenGateway.validateToken(null);

        // Assert
        assertFalse(isValid);
    }
} 