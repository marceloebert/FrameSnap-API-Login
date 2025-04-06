package com.fiap.framesnap.infrastructure.authentication.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CognitoConfigTest {

    private CognitoConfig cognitoConfig;

    @Mock
    private CognitoProperties cognitoProperties;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cognitoConfig = new CognitoConfig();
    }

    @Test
    void shouldCreateCognitoIdentityProviderClient() {
        // Act
        CognitoIdentityProviderClient client = cognitoConfig.cognitoIdentityProviderClient();

        // Assert
        assertNotNull(client);
    }

    @Test
    void shouldGetUserPoolId() {
        // Arrange
        String expectedUserPoolId = "test-user-pool-id";
        when(cognitoProperties.getUserPoolId()).thenReturn(expectedUserPoolId);

        // Act
        String actualUserPoolId = cognitoConfig.getUserPoolId();

        // Assert
        assertNotNull(actualUserPoolId);
        assertTrue(actualUserPoolId.length() > 0);
    }

    @Test
    void shouldGetClientId() {
        // Arrange
        String expectedClientId = "test-client-id";
        when(cognitoProperties.getClientId()).thenReturn(expectedClientId);

        // Act
        String actualClientId = cognitoConfig.getClientId();

        // Assert
        assertNotNull(actualClientId);
        assertTrue(actualClientId.length() > 0);
    }
} 