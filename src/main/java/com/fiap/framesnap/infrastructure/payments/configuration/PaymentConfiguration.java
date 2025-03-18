package com.fiap.framesnap.infrastructure.payments.configuration;

import com.fiap.framesnap.application.orders.usecases.UpdateOrderStateUseCase;
import com.fiap.framesnap.application.payments.gateways.PaymentGateway;
import com.fiap.framesnap.application.payments.usecases.ApprovePaymentUseCase;
import com.fiap.framesnap.application.payments.usecases.CreatePaymentUseCase;
import com.fiap.framesnap.infrastructure.payments.controller.mapper.PaymentDTOMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfiguration {

    private final PaymentGateway paymentGateway;
    private final UpdateOrderStateUseCase updateOrderStateUseCase;

    public PaymentConfiguration(PaymentGateway paymentGateway, UpdateOrderStateUseCase updateOrderStateUseCase) {
        this.paymentGateway = paymentGateway;
        this.updateOrderStateUseCase = updateOrderStateUseCase;
    }

    @Bean
    public CreatePaymentUseCase createPaymentUseCase() {
        return new CreatePaymentUseCase(paymentGateway);
    }

    @Bean
    public ApprovePaymentUseCase approvePaymentUseCase() {
        return new ApprovePaymentUseCase(paymentGateway, updateOrderStateUseCase);
    }

    @Bean
    public PaymentDTOMapper paymentDTOMapper() {
        return new PaymentDTOMapper();
    }
}
