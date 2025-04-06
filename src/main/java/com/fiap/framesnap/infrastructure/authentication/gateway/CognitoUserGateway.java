package com.fiap.framesnap.infrastructure.authentication.gateway;

import com.fiap.framesnap.application.authentication.gateways.UserGateway;
import com.fiap.framesnap.entities.authentication.User;
import com.fiap.framesnap.infrastructure.authentication.configuration.CognitoProperties;
import com.fiap.framesnap.crosscutting.util.CognitoSecretHashGenerator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Primary
public class CognitoUserGateway implements UserGateway {

    private final CognitoIdentityProviderClient cognitoClient;
    private final CognitoProperties cognitoProperties;

    public CognitoUserGateway(CognitoIdentityProviderClient cognitoClient, CognitoProperties cognitoProperties) {
        this.cognitoClient = cognitoClient;
        this.cognitoProperties = cognitoProperties;
    }

    @Override
    public User register(User user) {
        String clientSecret = cognitoProperties.getClientSecret();
        String secretHash = (clientSecret != null && !clientSecret.isEmpty())
                ? CognitoSecretHashGenerator.calculateSecretHash(
                cognitoProperties.getClientId(),
                clientSecret,
                user.getEmail()
        )
                : null;

        SignUpRequest.Builder signUpBuilder = SignUpRequest.builder()
                .clientId(cognitoProperties.getClientId())
                .username(user.getEmail())
                .password(user.getPassword())
                .userAttributes(List.of(
                        AttributeType.builder().name("email").value(user.getEmail()).build()
                ));

        if (secretHash != null) {
            signUpBuilder.secretHash(secretHash);
        }

        SignUpResponse response = cognitoClient.signUp(signUpBuilder.build());

        cognitoClient.adminConfirmSignUp(AdminConfirmSignUpRequest.builder()
                .userPoolId(cognitoProperties.getUserPoolId())
                .username(user.getEmail())
                .build());

        return new User(UUID.fromString(response.userSub()), user.getEmail(), user.getPassword());
    }

    @Override
    public Optional<User> findByEmail(String email) {
        ListUsersRequest listUsersRequest = ListUsersRequest.builder()
                .userPoolId(cognitoProperties.getUserPoolId())
                .filter("email = \"" + email + "\"")
                .build();

        ListUsersResponse response = cognitoClient.listUsers(listUsersRequest);

        if (response.users().isEmpty()) {
            return Optional.empty();
        }

        UserType cognitoUser = response.users().get(0);

        // Pega o ID correto usando o atributo "sub" (que é o UUID do usuário no Cognito)
        String userIdAttr = cognitoUser.attributes().stream()
                .filter(attr -> attr.name().equals("sub"))
                .findFirst()
                .map(AttributeType::value)
                .orElseThrow(() -> new RuntimeException("sub attribute not found"));

        String userEmail = cognitoUser.attributes().stream()
                .filter(attr -> attr.name().equals("email"))
                .findFirst()
                .map(AttributeType::value)
                .orElse(email);

        return Optional.of(new User(UUID.fromString(userIdAttr), userEmail, ""));
    }

    @Override
    public String login(String email, String password) {
        String secretHash = CognitoSecretHashGenerator.calculateSecretHash(
                cognitoProperties.getClientId(),
                cognitoProperties.getClientSecret(),
                email
        );

        Map<String, String> authParams = new HashMap<>();
        authParams.put("USERNAME", email);
        authParams.put("PASSWORD", password);
        authParams.put("SECRET_HASH", secretHash);

        InitiateAuthRequest authRequest = InitiateAuthRequest.builder()
                .clientId(cognitoProperties.getClientId())
                .authFlow(AuthFlowType.USER_PASSWORD_AUTH)
                .authParameters(authParams)
                .build();

        InitiateAuthResponse authResponse = cognitoClient.initiateAuth(authRequest);
        return authResponse.authenticationResult().idToken();
    }
}
