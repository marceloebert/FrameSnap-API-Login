package com.fiap.framesnap.infrastructure.authentication.gateway;

import com.fiap.framesnap.entities.authentication.User;
import com.fiap.framesnap.infrastructure.authentication.configuration.CognitoProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CognitoUserGatewayTest {

    @Mock
    private CognitoIdentityProviderClient cognitoClient;

    @Mock
    private CognitoProperties cognitoProperties;

    private CognitoUserGateway cognitoUserGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cognitoUserGateway = new CognitoUserGateway(cognitoClient, cognitoProperties);
    }

    @Test
    void shouldRegisterUser() {
        // Arrange
        UUID id = UUID.randomUUID();
        String email = "test@example.com";
        String password = "password123";
        User user = new User(id, email, password);

        SignUpResponse signUpResponse = SignUpResponse.builder()
                .userSub(id.toString())
                .build();

        when(cognitoProperties.getClientId()).thenReturn("test-client-id");
        when(cognitoProperties.getClientSecret()).thenReturn("test-client-secret");
        when(cognitoClient.signUp(any(SignUpRequest.class))).thenReturn(signUpResponse);

        // Act
        User registeredUser = cognitoUserGateway.register(user);

        // Assert
        assertNotNull(registeredUser);
        assertEquals(email, registeredUser.getEmail());
    }

    @Test
    void shouldFindUserByEmail() {
        // Arrange
        String email = "test@example.com";
        AdminGetUserResponse userResponse = AdminGetUserResponse.builder()
                .username(email)
                .build();

        when(cognitoProperties.getUserPoolId()).thenReturn("test-user-pool-id");
        when(cognitoClient.adminGetUser(any(AdminGetUserRequest.class))).thenReturn(userResponse);

        // Act
        var foundUser = cognitoUserGateway.findByEmail(email);

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals(email, foundUser.get().getEmail());
    }

    @Test
    void shouldReturnNullWhenUserNotFound() {
        // Arrange
        String email = "nonexistent@example.com";

        when(cognitoProperties.getUserPoolId()).thenReturn("test-user-pool-id");
        when(cognitoClient.adminGetUser(any(AdminGetUserRequest.class)))
                .thenThrow(UserNotFoundException.builder().message("User not found").build());

        // Act
        var foundUser = cognitoUserGateway.findByEmail(email);

        // Assert
        assertTrue(foundUser.isEmpty());
    }

    @Test
    void shouldLoginUser() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        String expectedToken = "test-token";

        InitiateAuthResponse authResponse = InitiateAuthResponse.builder()
                .authenticationResult(AuthenticationResultType.builder()
                        .idToken(expectedToken)
                        .build())
                .build();

        when(cognitoProperties.getClientId()).thenReturn("test-client-id");
        when(cognitoProperties.getClientSecret()).thenReturn("test-client-secret");
        when(cognitoClient.initiateAuth(any(InitiateAuthRequest.class))).thenReturn(authResponse);

        // Act
        String token = cognitoUserGateway.login(email, password);

        // Assert
        assertNotNull(token);
        assertEquals(expectedToken, token);
    }

    @Test
    void shouldReturnNullForInvalidLogin() {
        // Arrange
        String email = "test@example.com";
        String password = "wrong-password";

        when(cognitoProperties.getClientId()).thenReturn("test-client-id");
        when(cognitoProperties.getClientSecret()).thenReturn("test-client-secret");
        when(cognitoClient.initiateAuth(any(InitiateAuthRequest.class)))
                .thenThrow(NotAuthorizedException.builder().message("Invalid credentials").build());

        // Act
        String token = cognitoUserGateway.login(email, password);

        // Assert
        assertNull(token);
    }
} 