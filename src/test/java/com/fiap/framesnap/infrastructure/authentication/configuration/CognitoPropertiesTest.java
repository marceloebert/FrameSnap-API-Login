package com.fiap.framesnap.infrastructure.authentication.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class CognitoPropertiesTest {

    private CognitoProperties cognitoProperties;

    @BeforeEach
    void setUp() {
        cognitoProperties = new CognitoProperties();
    }

    @Test
    void shouldGetClientId() {
        // Arrange
        String expectedClientId = "test-client-id";
        ReflectionTestUtils.setField(cognitoProperties, "clientId", expectedClientId);

        // Act
        String clientId = cognitoProperties.getClientId();

        // Assert
        assertEquals(expectedClientId, clientId);
    }

    @Test
    void shouldGetClientSecret() {
        // Arrange
        String expectedClientSecret = "test-client-secret";
        ReflectionTestUtils.setField(cognitoProperties, "clientSecret", expectedClientSecret);

        // Act
        String clientSecret = cognitoProperties.getClientSecret();

        // Assert
        assertEquals(expectedClientSecret, clientSecret);
    }

    @Test
    void shouldGetUserPoolId() {
        // Arrange
        String expectedUserPoolId = "test-user-pool-id";
        ReflectionTestUtils.setField(cognitoProperties, "userPoolId", expectedUserPoolId);

        // Act
        String userPoolId = cognitoProperties.getUserPoolId();

        // Assert
        assertEquals(expectedUserPoolId, userPoolId);
    }

    @Test
    void shouldGetAccessKey() {
        // Arrange
        String expectedAccessKey = "test-access-key";
        ReflectionTestUtils.setField(cognitoProperties, "accessKey", expectedAccessKey);

        // Act
        String accessKey = cognitoProperties.getAccessKey();

        // Assert
        assertEquals(expectedAccessKey, accessKey);
    }

    @Test
    void shouldGetSecretKey() {
        // Arrange
        String expectedSecretKey = "test-secret-key";
        ReflectionTestUtils.setField(cognitoProperties, "secretKey", expectedSecretKey);

        // Act
        String secretKey = cognitoProperties.getSecretKey();

        // Assert
        assertEquals(expectedSecretKey, secretKey);
    }

    @Test
    void shouldGetRegion() {
        // Arrange
        String expectedRegion = "test-region";
        ReflectionTestUtils.setField(cognitoProperties, "region", expectedRegion);

        // Act
        String region = cognitoProperties.getRegion();

        // Assert
        assertEquals(expectedRegion, region);
    }
} 