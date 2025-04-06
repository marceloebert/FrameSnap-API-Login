package com.fiap.framesnap.crosscutting.config;

import org.junit.jupiter.api.Test;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DynamoDBConfigTest {

    private final DynamoDBConfig config = new DynamoDBConfig();

    @Test
    void shouldCreateDynamoDbClient() {
        // Act
        DynamoDbClient client = config.dynamoDbClient();

        // Assert
        assertNotNull(client);
    }

    @Test
    void shouldCreateDynamoDbEnhancedClient() {
        // Arrange
        DynamoDbClient client = config.dynamoDbClient();

        // Act
        DynamoDbEnhancedClient enhancedClient = config.dynamoDbEnhancedClient(client);

        // Assert
        assertNotNull(enhancedClient);
    }
} 