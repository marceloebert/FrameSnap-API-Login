package com.fiap.framesnap.infrastructure.authentication.configuration;

import com.fiap.framesnap.application.authentication.gateways.TokenGateway;
import com.fiap.framesnap.application.authentication.gateways.UserGateway;
import com.fiap.framesnap.application.authentication.usecases.LoginUseCase;
import com.fiap.framesnap.application.authentication.usecases.RegisterUserUseCase;
import com.fiap.framesnap.application.authentication.usecases.ValidateTokenUseCase;
import com.fiap.framesnap.application.authentication.usecases.GenerateTokenUseCase;
import com.fiap.framesnap.infrastructure.authentication.gateway.TokenGatewayImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationConfigurationTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private TokenGateway tokenGateway;

    @Mock
    private CognitoIdentityProviderClient cognitoClient;

    private AuthenticationConfiguration config;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        config = new AuthenticationConfiguration();
    }

    @Test
    void shouldCreateLoginUseCase() {
        // Act
        LoginUseCase loginUseCase = config.loginUseCase(userGateway, tokenGateway);

        // Assert
        assertNotNull(loginUseCase);
    }

    @Test
    void shouldCreateRegisterUserUseCase() {
        // Act
        RegisterUserUseCase registerUserUseCase = config.registerUserUseCase(userGateway);

        // Assert
        assertNotNull(registerUserUseCase);
    }

    @Test
    void shouldCreateValidateTokenUseCase() {
        // Act
        ValidateTokenUseCase validateTokenUseCase = config.validateTokenUseCase(tokenGateway);

        // Assert
        assertNotNull(validateTokenUseCase);
    }

    @Test
    void shouldCreateGenerateTokenUseCase() {
        // Act
        GenerateTokenUseCase generateTokenUseCase = config.generateTokenUseCase(tokenGateway);

        // Assert
        assertNotNull(generateTokenUseCase);
    }

    @Test
    void shouldCreateTokenGateway() {
        // Act
        TokenGateway tokenGateway = config.tokenGateway(cognitoClient);

        // Assert
        assertNotNull(tokenGateway);
        assertTrue(tokenGateway instanceof TokenGatewayImpl);
    }
} 