package com.fiap.framesnap.infrastructure.authentication.configuration;

import com.fiap.framesnap.application.authentication.gateways.TokenGateway;
import com.fiap.framesnap.application.authentication.gateways.UserGateway;
import com.fiap.framesnap.application.authentication.usecases.LoginUseCase;
import com.fiap.framesnap.application.authentication.usecases.RegisterUserUseCase;
import com.fiap.framesnap.application.authentication.usecases.ValidateTokenUseCase;
import com.fiap.framesnap.application.authentication.usecases.GenerateTokenUseCase;
import com.fiap.framesnap.infrastructure.authentication.gateway.TokenGatewayImpl;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthenticationConfiguration {

    @Bean
    public LoginUseCase loginUseCase(UserGateway userGateway, TokenGateway tokenGateway) {
        return new LoginUseCase(userGateway, tokenGateway);
    }

    @Bean
    public RegisterUserUseCase registerUserUseCase(UserGateway userGateway) {
        return new RegisterUserUseCase(userGateway);
    }

    @Bean
    public ValidateTokenUseCase validateTokenUseCase(TokenGateway tokenGateway) {
        return new ValidateTokenUseCase(tokenGateway);
    }

    @Bean
    public GenerateTokenUseCase generateTokenUseCase(TokenGateway tokenGateway) {
        return new GenerateTokenUseCase(tokenGateway);
    }

    @Bean
    public TokenGateway tokenGateway(CognitoIdentityProviderClient cognitoClient) {
        return new TokenGatewayImpl(cognitoClient);
    }
}
