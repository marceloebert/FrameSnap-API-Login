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
        String accessKey = "ASIA2WJTGM7JQ3MYHQ5K";
        String secretKey = "C9JlgzIPvpP6HOQgAcAV331yRSebaBOezjnysm4/";
        String sessionToken = "IQoJb3JpZ2luX2VjEDsaCXVzLXdlc3QtMiJHMEUCIQCcHV6V3H0ZepvKwA04qGI1bFF9akM87uDQdMq3I9zFMgIgV4ENyMk//veVNXAJB2gR/2xA5GvRzIwnQz0kikMUh4oqugIIlP//////////ARAAGgw3MzUwODM2NTMwNzUiDIm1qs+J9HJffH1GeSqOAt/3zg7lP1eYYRi6YTVUAsigENsd4h3B1kIF3vO1qhAHdq3GyoPi9PyXchUKXRk2jN/sbZkP1R55LPUEjvZDshZKXiyAi6eq/riAGiNJ7uBAwxC22cxN4/Es+auF5dy8Gbvi97IPv384ODUcEz0G1mpIHN82H060Xx5twgphoqaZciQQtDZCTbEQ6BaQv17z3jnC6QLoXRB7zEa1dCeLoF96F+ETKse3DmJwV1sXxlzHVrLrCzzoUn55n1s0/1GIIIusVqigp2zZ1t0kzX9GLOrHu112JDVVM6FkSALSv94UYMKCY3TtzaoztBtUnzATXrsIkW67qID1bcyjVeuiUUS7owm4++8WsTqBuqFPRzCKvPG+BjqdAc7ILv4hLKm8xtr9pYo+DNopzEx7s0T+3yBlqJsYEsn3GN5jjNfRjOt2RLJGH3sts3PoR5TRU8JpXL2hTR9i8T+hpZdRwff1sAe7WYutvjjm+maMUEclb/yfFfIhBvptOVGEhMXd01ZmU60B0sRqvaQRpjySmLYFrkcL1wIJFI3trbL2mFPPQxse77eklmsU1wqfO9SM0hoPltwiymw=";

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
