package com.fiap.framesnap.application.orders.usecases;

import com.fiap.framesnap.application.orders.gateways.OrderGateway;
import com.fiap.framesnap.crosscutting.util.AppConfig;
import com.fiap.framesnap.crosscutting.util.RestClient;
import com.fiap.framesnap.entities.orders.Order;
import com.fiap.framesnap.entities.payments.Payment;
import com.fiap.framesnap.entities.orders.enums.OrderState;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;
import java.util.Map;
import org.springframework.http.ResponseEntity;


@Service
public class GetAllOrdersUseCase {

    private final OrderGateway orderGateway;
    private final RestClient restClient;
    private final AppConfig appConfig;

    public GetAllOrdersUseCase(OrderGateway orderGateway, RestClient restClient,  AppConfig appConfig) {
        this.orderGateway = orderGateway;
        this.restClient = restClient;
        this.appConfig = appConfig;
    }

    public List<Order> getAll() {
        List<Order> allOrders = orderGateway.findAll();

        List<Order> filteredOrders = allOrders.stream()
                .filter(order -> order.getState() != OrderState.FINISHED)
                .collect(Collectors.toList());

        // Para cada pedido, buscar o pagamento via API
        List<Order> ordersWithPayments = filteredOrders.stream()
                .map(order -> {
                    // Chamar a API para buscar o pagamento do pedido
                    Payment payment = fetchPaymentFromApi(order.getId());

                    // Atualizar o pedido com o pagamento recebido
                    order.setPayment(payment);

                    // Buscar status da produção via API
                    String productionState = fetchProductionStateFromApi(order.getId());
                    if (productionState != null) {
                        order.setState(OrderState.valueOf(productionState));
                    }

                    return order;
                })
                .sorted(Comparator.comparing((Order order) -> getOrderPriority(order.getState()))
                        .thenComparing(Order::getCreationTime))
                .collect(Collectors.toList());

        return ordersWithPayments;
    }

    private int getOrderPriority(OrderState state) {
        switch (state) {
            case READY:
                return 1;
            case PREPARING:
                return 2;
            case RECEIVED:
                return 3;
            default:
                return Integer.MAX_VALUE;
        }
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
