package com.fiap.framesnap.crosscutting.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;


@Configuration
public class DynamoDBConfig {

    @Bean
    public DynamoDbClient dynamoDbClient() {
        /*String region = System.getenv("DYNAMODB_REGION");
        String accessKey = System.getenv("AWS_ACCESS_KEY_ID");
        String secretKey = System.getenv("AWS_SECRET_ACCESS_KEY");
        String sessionToken = System.getenv("AWS_SESSION_TOKEN");*/

        String region = "us-east-1";
        String accessKey = "ASIAR3F4HGIBNSKFBY3V";
        String secretKey = "+KeoYabTL/1Xgq+D5QhmuyDSMIFb4dJGXZ2l9jN1";
        String sessionToken = "IQoJb3JpZ2luX2VjEEIaCXVzLXdlc3QtMiJGMEQCICurtKdS66wlJTta9OJ0e7PhuLBpWvpSqa6CM9NuKBNUAiB3YrfuTB5DeLuxTKBnmguDX10Npfr7ZxHINhLuOX5m1iqxAghrEAAaDDEyNzA5NjI3MTM2MiIMITWHI0f2/BS9hjaMKo4C6yStEcMbHlWw2aN4byfiu4UMvmYe7QWbRN3nwtTEafi6d1gYmU1N2nn/xi+4FJWrUY8BfhD/kNV/dSS5yk0BN/X/IFnLctRqa2+OPaqsAJxM5biF3v7zUETB+QaNsuFHBdNKf+zwQBamjyIjbi4QS8bpQn3xqWd4riIUfs6e1ebFePXIFKJvbnHUOmGWIQ6MiwWsi0gka+tFmoFgv+PbC034d+0mzuOqEUM9u/7Q/BvnowFmLRx/co/5Aem16Lqj8+Szd/AEPeH5bww0ivd4ywiPneUtX+UgfzUWizkm0v247KoUK7+/QVVmpC7qn9W91Cwx6b75sITpwIVGuthE4VSB6J7ruLtXHtEhZZ5pMKCnyr0GOp4BHPz0MAYXwPcndlfigNDZYvjA7/fHlScU2WYXOShLdIKCatPjYOaLVzHKzvBvfMtfwvFnGvrL0xTNpli6EouZGA/SKYzLbH/wNd4KI7jQTlYVN3q/dO9Rsp/p7b8rlnGPmv5WK2nWyFBMk6OcWyr5sVq88rP3gZMdlnsRYDfeTIXT/aSiIjeEpwO8/4RIqcG8ek9oShX+HLauKnsM1rk=";

        return DynamoDbClient.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsSessionCredentials.create(accessKey,secretKey,sessionToken)
                ))
                .build();
    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }
}
