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
import java.util.HashMap;
import java.util.Map;

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
        when(cognitoProperties.getUserPoolId()).thenReturn("test-pool-id");
        when(cognitoProperties.getClientId()).thenReturn("test-client-id");
        when(cognitoProperties.getClientSecret()).thenReturn("test-client-secret");
        cognitoUserGateway = new CognitoUserGateway(cognitoClient, cognitoProperties);
    }

    @Test
    void shouldRegisterUser() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        String userId = UUID.randomUUID().toString();
        
        User user = new User(null, email, password);
        
        Map<String, String> attributes = new HashMap<>();
        attributes.put("email", email);
        
        AdminCreateUserRequest createUserRequest = AdminCreateUserRequest.builder()
            .userPoolId("test-pool-id")
            .username(email)
            .temporaryPassword(password)
            .userAttributes(AttributeType.builder()
                .name("email")
                .value(email)
                .build())
            .build();
            
        AdminCreateUserResponse createUserResponse = AdminCreateUserResponse.builder()
            .user(UserType.builder()
                .username(userId)
                .attributes(AttributeType.builder()
                    .name("email")
                    .value(email)
                    .build())
                .build())
            .build();
            
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
        
        ListUsersRequest listUsersRequest = ListUsersRequest.builder()
            .userPoolId("test-pool-id")
            .filter("email = \"" + email + "\"")
            .build();
            
        ListUsersResponse listUsersResponse = ListUsersResponse.builder()
            .users(Collections.singletonList(UserType.builder()
                .username(userId)
                .attributes(AttributeType.builder()
                    .name("email")
                    .value(email)
                    .build())
                .build()))
            .build();
            
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
        
        ListUsersRequest listUsersRequest = ListUsersRequest.builder()
            .userPoolId("test-pool-id")
            .filter("email = \"" + email + "\"")
            .build();
            
        ListUsersResponse listUsersResponse = ListUsersResponse.builder()
            .users(Collections.emptyList())
            .build();
            
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
        
        AdminInitiateAuthRequest authRequest = AdminInitiateAuthRequest.builder()
            .userPoolId("test-pool-id")
            .clientId("test-client-id")
            .authFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
            .authParameters(Map.of(
                "USERNAME", email,
                "SECRET_HASH", "test-client-secret"
            ))
            .build();
            
        AdminInitiateAuthResponse authResponse = AdminInitiateAuthResponse.builder()
            .authenticationResult(AuthenticationResultType.builder()
                .accessToken(expectedToken)
                .build())
            .build();
            
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