package com.fiap.framesnap.infrastructure.authentication.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;

@Configuration
public class CognitoConfiguration {

    @Bean
    public CognitoIdentityProviderClient cognitoClient(CognitoProperties cognitoProperties) {
        AwsSessionCredentials awsCredentials = AwsSessionCredentials.create(
                cognitoProperties.getAccessKey(),
                cognitoProperties.getSecretKey(),
                cognitoProperties.getSessionToken()
        );

        return CognitoIdentityProviderClient.builder()
                .region(Region.of(cognitoProperties.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();
    }
} 