package com.fiap.framesnap.application.orders.usecases;

import com.fiap.framesnap.infrastructure.payments.controller.dto.CreatePaymentRequest;
import com.fiap.framesnap.application.orders.gateways.OrderGateway;
import com.fiap.framesnap.application.products.gateways.ProductGateway;
import com.fiap.framesnap.application.customers.gateways.CustomerGateway;
import com.fiap.framesnap.application.payments.gateways.PaymentGateway;
import com.fiap.framesnap.entities.orders.Order;
import com.fiap.framesnap.entities.orders.OrderItem;
import com.fiap.framesnap.entities.payments.Payment;
import com.fiap.framesnap.entities.products.Product;
import com.fiap.framesnap.entities.customers.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.fiap.framesnap.crosscutting.util.RestClient;
import com.fiap.framesnap.crosscutting.util.AppConfig;
import java.util.Map;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CreateOrderUseCase {

    private final OrderGateway orderGateway;
    private final ProductGateway productGateway;
    private final CustomerGateway customerGateway;
    private final PaymentGateway paymentGateway;
    private final RestClient restClient;
    private final AppConfig appConfig;

    @Autowired
    public CreateOrderUseCase(OrderGateway orderGateway, ProductGateway productGateway, CustomerGateway customerGateway, PaymentGateway paymentGateway, RestClient restClient,  AppConfig appConfig) {
        this.orderGateway = orderGateway;
        this.productGateway = productGateway;
        this.customerGateway = customerGateway;
        this.paymentGateway = paymentGateway;
        this.restClient = restClient;
        this.appConfig = appConfig;
    }

    public Order createOrder(Order order, String customerDocument) {

        Customer customer;
        if (customerDocument == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        else {

            customer = customerGateway.findCustomer(customerDocument)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        }


        List<OrderItem> validatedItems = order.getItems().stream()
                .map(item -> {
                    Product product = productGateway.getById(item.getProductId())
                            .orElseThrow(() -> new IllegalArgumentException("Product not found"));
                    return new OrderItem(
                            UUID.randomUUID(),
                            item.getProductId(),
                            item.getQuantity(),
                            item.getObservation(),
                            item.getPrice(),
                            false
                    );
                })
                .collect(Collectors.toList());

        // Criando o pedido e salvando no banco do DynamoDB
        Order validatedOrder = new Order(customer, validatedItems);
        Order savedOrder = orderGateway.save(validatedOrder);

        // Criando o payload para a API de Pagamento
        CreatePaymentRequest paymentRequest = new CreatePaymentRequest();
        paymentRequest.setOrderId(savedOrder.getId());
        paymentRequest.setAmount(savedOrder.getTotalPrice());

        //Chamando a API de Pagamentos
        ResponseEntity<Payment> response = restClient.post(appConfig.getCreatePaymentApiUrl(), paymentRequest, Payment.class, Map.of());

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            savedOrder.setPayment(response.getBody());
        }

        String productionUrl = appConfig.getCreateProductionApiUrl() + "/" + savedOrder.getId();
        Map<String, String> productionPayload = Map.of("state", "PENDING");

        ResponseEntity<String> productionResponse = restClient.post(productionUrl, productionPayload, String.class, Map.of());

        if (!productionResponse.getStatusCode().is2xxSuccessful()) {
            System.err.println("Error to create State of Production " + savedOrder.getId());
        }


        return orderGateway.save(savedOrder);
    }
}
