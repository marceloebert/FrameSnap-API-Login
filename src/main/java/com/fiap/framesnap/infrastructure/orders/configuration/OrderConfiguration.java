package com.fiap.framesnap.infrastructure.orders.configuration;

import com.fiap.framesnap.application.customers.gateways.CustomerGateway;
import com.fiap.framesnap.application.orders.usecases.CreateOrderUseCase;
import com.fiap.framesnap.application.orders.usecases.GetAllOrdersUseCase;
import com.fiap.framesnap.application.orders.usecases.GetOrderByIdUseCase;
import com.fiap.framesnap.application.orders.usecases.UpdateOrderStateUseCase;
import com.fiap.framesnap.application.orders.gateways.OrderGateway;
import com.fiap.framesnap.application.payments.gateways.PaymentGateway;
import com.fiap.framesnap.application.products.gateways.ProductGateway;
import com.fiap.framesnap.crosscutting.util.AppConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fiap.framesnap.crosscutting.util.RestClient;

@Configuration
public class OrderConfiguration {

    @Bean
    public CreateOrderUseCase createOrderUseCase(OrderGateway orderGateway, ProductGateway productGateway, CustomerGateway customerGateway, PaymentGateway paymentGateway,RestClient restClient, AppConfig appconfig) {
        return new CreateOrderUseCase(orderGateway, productGateway,customerGateway,paymentGateway,restClient,appconfig);
    }

    @Bean
    public GetAllOrdersUseCase getAllOrdersUseCase(OrderGateway orderGateway,RestClient restClient, AppConfig appconfig) {
        return new GetAllOrdersUseCase(orderGateway,restClient,appconfig);
    }

    @Bean
    public GetOrderByIdUseCase getOrderByIdUseCase(OrderGateway orderGateway,RestClient restClient, AppConfig appconfig) {
        return new GetOrderByIdUseCase(orderGateway,restClient,appconfig);
    }

    @Bean
    public UpdateOrderStateUseCase updateOrderStateUseCase(OrderGateway orderGateway) {
        return new UpdateOrderStateUseCase(orderGateway);
    }
}
