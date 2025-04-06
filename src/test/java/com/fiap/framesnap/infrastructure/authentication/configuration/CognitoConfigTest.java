package com.fiap.framesnap.infrastructure.authentication.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class CognitoConfigTest {

    private CognitoConfig cognitoConfig;

    @Mock
    private CognitoProperties cognitoProperties;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cognitoConfig = new CognitoConfig();
        ReflectionTestUtils.setField(cognitoConfig, "accessKey", "test-access-key");
        ReflectionTestUtils.setField(cognitoConfig, "secretKey", "test-secret-key");
        ReflectionTestUtils.setField(cognitoConfig, "region", "us-east-1");
        ReflectionTestUtils.setField(cognitoConfig, "sessionToken", "test-session-token");
    }

    @Test
    void shouldCreateCognitoIdentityProviderClient() {
        // Act
        CognitoIdentityProviderClient client = cognitoConfig.cognitoIdentityProviderClient();

        // Assert
        assertNotNull(client);
    }
} 