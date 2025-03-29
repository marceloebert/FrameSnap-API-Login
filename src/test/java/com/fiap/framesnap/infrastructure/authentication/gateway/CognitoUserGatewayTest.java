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
import java.util.List;

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
        
        SignUpResponse signUpResponse = SignUpResponse.builder()
            .userSub(userId)
            .build();
            
        when(cognitoClient.signUp(any(SignUpRequest.class)))
            .thenReturn(signUpResponse);
            
        when(cognitoClient.adminConfirmSignUp(any(AdminConfirmSignUpRequest.class)))
            .thenReturn(AdminConfirmSignUpResponse.builder().build());

        // Act
        User result = cognitoUserGateway.register(user);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getId().toString());
        assertEquals(email, result.getEmail());
        assertEquals(password, result.getPassword());
        verify(cognitoClient).signUp(any(SignUpRequest.class));
        verify(cognitoClient).adminConfirmSignUp(any(AdminConfirmSignUpRequest.class));
    }

    @Test
    void shouldFindUserByEmail() {
        // Arrange
        String email = "test@example.com";
        String userId = UUID.randomUUID().toString();
        
        AttributeType emailAttribute = AttributeType.builder()
            .name("email")
            .value(email)
            .build();
            
        UserType cognitoUser = UserType.builder()
            .username(userId)
            .attributes(Collections.singletonList(emailAttribute))
            .build();
            
        ListUsersResponse listUsersResponse = ListUsersResponse.builder()
            .users(Collections.singletonList(cognitoUser))
            .build();
            
        when(cognitoClient.listUsers(any(ListUsersRequest.class)))
            .thenReturn(listUsersResponse);

        // Act
        Optional<User> result = cognitoUserGateway.findByEmail(email);

        // Assert
        assertTrue(result.isPresent());
        User user = result.get();
        assertEquals(userId, user.getId().toString());
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
        
        Map<String, String> authParams = new HashMap<>();
        authParams.put("USERNAME", email);
        authParams.put("PASSWORD", password);
        authParams.put("SECRET_HASH", "test-client-secret");
        
        InitiateAuthRequest authRequest = InitiateAuthRequest.builder()
            .clientId("test-client-id")
            .authFlow(AuthFlowType.USER_PASSWORD_AUTH)
            .authParameters(authParams)
            .build();
            
        AuthenticationResultType authResult = AuthenticationResultType.builder()
            .idToken(expectedToken)
            .build();
            
        InitiateAuthResponse authResponse = InitiateAuthResponse.builder()
            .authenticationResult(authResult)
            .build();
            
        when(cognitoClient.initiateAuth(any(InitiateAuthRequest.class)))
            .thenReturn(authResponse);

        // Act
        String result = cognitoUserGateway.login(email, password);

        // Assert
        assertNotNull(result);
        assertEquals(expectedToken, result);
        verify(cognitoClient).initiateAuth(any(InitiateAuthRequest.class));
    }
} 