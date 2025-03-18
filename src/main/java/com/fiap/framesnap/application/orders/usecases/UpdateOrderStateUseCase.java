package com.fiap.framesnap.application.orders.usecases;

import com.fiap.framesnap.application.orders.gateways.OrderGateway;
import com.fiap.framesnap.entities.orders.Order;
import com.fiap.framesnap.entities.orders.enums.OrderState;

import java.util.Optional;
import java.util.UUID;

public class UpdateOrderStateUseCase {

    private final OrderGateway orderGateway;

    public UpdateOrderStateUseCase(OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    public Order updateOrderState(UUID id, String newState) {
        Optional<Order> optionalOrder = orderGateway.findById(id);

        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            OrderState orderState = OrderState.valueOf(newState.toUpperCase());
            order.updateState(orderState);

            return orderGateway.save(order);
        } else {
            throw new IllegalArgumentException("Order not found");
        }
    }
}

