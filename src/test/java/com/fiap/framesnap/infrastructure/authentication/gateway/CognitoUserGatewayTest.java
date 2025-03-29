package com.fiap.framesnap.infrastructure.authentication.gateway;

import com.fiap.framesnap.application.authentication.gateways.UserGateway;
import com.fiap.framesnap.entities.authentication.User;
import com.fiap.framesnap.infrastructure.authentication.configuration.CognitoProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.util.Optional;
import java.util.UUID;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        String email = "test@example.com";
        String password = "password123";
        String userId = UUID.randomUUID().toString();
        
        User user = new User(null, email, password);
        
        AdminCreateUserResponse createUserResponse = AdminCreateUserResponse.builder()
            .user(software.amazon.awssdk.services.cognitoidentityprovider.model.UserType.builder()
                .username(userId)
                .build())
            .build();
            
        when(cognitoProperties.getUserPoolId()).thenReturn("userPoolId");
        when(cognitoProperties.getClientId()).thenReturn("clientId");
        when(cognitoProperties.getClientSecret()).thenReturn("clientSecret");
        when(cognitoClient.adminCreateUser(any(AdminCreateUserRequest.class)))
            .thenReturn(createUserResponse);

        // Act
        User result = cognitoUserGateway.register(user);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals(email, result.getEmail());
        assertEquals(password, result.getPassword());
        verify(cognitoClient).adminCreateUser(any(AdminCreateUserRequest.class));
    }

    @Test
    void shouldFindUserByEmail() {
        // Arrange
        String email = "test@example.com";
        String userId = UUID.randomUUID().toString();
        
        ListUsersResponse listUsersResponse = ListUsersResponse.builder()
            .users(Collections.singletonList(software.amazon.awssdk.services.cognitoidentityprovider.model.UserType.builder()
                .username(userId)
                .attributes(AttributeType.builder()
                    .name("email")
                    .value(email)
                    .build())
                .build()))
            .build();
            
        when(cognitoProperties.getUserPoolId()).thenReturn("userPoolId");
        when(cognitoClient.listUsers(any(ListUsersRequest.class)))
            .thenReturn(listUsersResponse);

        // Act
        Optional<User> result = cognitoUserGateway.findByEmail(email);

        // Assert
        assertTrue(result.isPresent());
        User user = result.get();
        assertEquals(userId, user.getId());
        assertEquals(email, user.getEmail());
        verify(cognitoClient).listUsers(any(ListUsersRequest.class));
    }

    @Test
    void shouldReturnEmptyWhenUserNotFound() {
        // Arrange
        String email = "nonexistent@example.com";
        
        ListUsersResponse listUsersResponse = ListUsersResponse.builder()
            .users(Collections.emptyList())
            .build();
            
        when(cognitoProperties.getUserPoolId()).thenReturn("userPoolId");
        when(cognitoClient.listUsers(any(ListUsersRequest.class)))
            .thenReturn(listUsersResponse);

        // Act
        Optional<User> result = cognitoUserGateway.findByEmail(email);

        // Assert
        assertFalse(result.isPresent());
        verify(cognitoClient).listUsers(any(ListUsersRequest.class));
    }

    @Test
    void shouldLoginUser() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        String expectedToken = "jwt-token";
        
        AdminInitiateAuthResponse authResponse = AdminInitiateAuthResponse.builder()
            .authenticationResult(AuthenticationResultType.builder()
                .accessToken(expectedToken)
                .build())
            .build();
            
        when(cognitoProperties.getUserPoolId()).thenReturn("userPoolId");
        when(cognitoProperties.getClientId()).thenReturn("clientId");
        when(cognitoProperties.getClientSecret()).thenReturn("clientSecret");
        when(cognitoClient.adminInitiateAuth(any(AdminInitiateAuthRequest.class)))
            .thenReturn(authResponse);

        // Act
        String result = cognitoUserGateway.login(email, password);

        // Assert
        assertNotNull(result);
        assertEquals(expectedToken, result);
        verify(cognitoClient).adminInitiateAuth(any(AdminInitiateAuthRequest.class));
    }
} 