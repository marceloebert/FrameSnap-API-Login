package com.fiap.framesnap.infrastructure.authentication.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CognitoProperties {

    @Value("${aws.cognito.client-id}")
    private String clientId;

    @Value("${aws.cognito.client-secret:}") // Se não for definido, assume string vazia
    private String clientSecret;

    @Value("${aws.cognito.user-pool-id}")
    private String userPoolId;

    @Value("${aws.cognito.access-key}")
    private String accessKey;

    @Value("${aws.cognito.secret-key}")
    private String secretKey;

    @Value("${aws.cognito.region}")
    private String region;

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getUserPoolId() {
        return userPoolId;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getRegion() {
        return region;
    }
}
