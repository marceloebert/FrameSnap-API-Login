package com.fiap.framesnap.application.orders.usecases;

import com.fiap.framesnap.application.orders.gateways.OrderGateway;
import com.fiap.framesnap.crosscutting.util.AppConfig;
import com.fiap.framesnap.crosscutting.util.RestClient;
import com.fiap.framesnap.entities.orders.Order;
import com.fiap.framesnap.entities.orders.enums.OrderState;
import com.fiap.framesnap.entities.payments.Payment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;



@Service
public class GetOrderByIdUseCase {

    private final OrderGateway orderGateway;
    private final RestClient restClient;
    private final AppConfig appConfig;

    public GetOrderByIdUseCase(OrderGateway orderGateway, RestClient restClient,  AppConfig appConfig) {
        this.orderGateway = orderGateway;
        this.restClient = restClient;
        this.appConfig = appConfig;
    }

    public Optional<Order> getOrderById(UUID id) {
        Optional<Order> orderOptional = orderGateway.findById(id);

        if (orderOptional.isEmpty()) {
            return Optional.empty();
        }

        Order order = orderOptional.get();
        Payment payment = fetchPaymentFromApi(order.getId());


        if (payment != null) {
            order.setPayment(payment);
        }

        String productionState = fetchProductionStateFromApi(order.getId());
        if (productionState != null) {
            order.setState(OrderState.valueOf(productionState));
        }

        return Optional.of(order);
    }

    private Payment fetchPaymentFromApi(UUID orderId) {
        try {
            String url = appConfig.getGetPaymentApiUrl() + "/" + orderId;

            ResponseEntity<Payment> response = restClient.get(url, Payment.class, Map.of());

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar pagamento para o pedido " + orderId + ": " + e.getMessage());
        }

        return null;
    }

    private String fetchProductionStateFromApi(UUID orderId) {
        try {
            String url = appConfig.getProductionApiUrl().replace("{ORDER-ID}",orderId.toString());

            ResponseEntity<String> response = restClient.get(url, String.class, Map.of());

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody().replaceAll("\"", ""); // Remover aspas JSON
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar status de produção para pedido " + orderId + ": " + e.getMessage());
        }

        return null;
    }
}
