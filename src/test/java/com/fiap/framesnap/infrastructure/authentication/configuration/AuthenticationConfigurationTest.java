package com.fiap.framesnap.infrastructure.authentication.configuration;

import com.fiap.framesnap.application.authentication.gateways.TokenGateway;
import com.fiap.framesnap.application.authentication.gateways.UserGateway;
import com.fiap.framesnap.application.authentication.usecases.LoginUseCase;
import com.fiap.framesnap.application.authentication.usecases.RegisterUserUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationConfigurationTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private TokenGateway tokenGateway;

    private final AuthenticationConfiguration config = new AuthenticationConfiguration();

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
} 