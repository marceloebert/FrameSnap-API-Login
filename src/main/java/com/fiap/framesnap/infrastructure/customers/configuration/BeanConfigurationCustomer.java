package com.fiap.framesnap.infrastructure.customers.configuration;

import com.fiap.framesnap.application.customers.usecases.RegisterCustomerUseCase;
import com.fiap.framesnap.application.customers.usecases.FindCustomerUseCase;
import com.fiap.framesnap.application.customers.gateways.CustomerGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfigurationCustomer {

    // Define o bean para o caso de uso de encontrar cliente
    @Bean
    FindCustomerUseCase findCustomerUseCase(CustomerGateway customerGateway){
        return new FindCustomerUseCase(customerGateway);
    }

    // Define o bean para o caso de uso de registrar cliente
    @Bean
    RegisterCustomerUseCase registerCustomerUseCase(CustomerGateway customerGateway){
        return new RegisterCustomerUseCase(customerGateway);
    }
}