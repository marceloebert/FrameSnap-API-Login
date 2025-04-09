package com.fiap.framesnap.infrastructure.authentication.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;

@Configuration
public class CognitoConfiguration {

    @Bean
    public CognitoIdentityProviderClient cognitoClient(CognitoProperties cognitoProperties) {
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(
                cognitoProperties.getAccessKey(),
                cognitoProperties.getSecretKey()
        );

        return CognitoIdentityProviderClient.builder()
                .region(Region.of(cognitoProperties.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();
    }
} 