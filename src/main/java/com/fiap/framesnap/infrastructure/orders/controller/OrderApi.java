package com.fiap.framesnap.infrastructure.orders.controller;

import com.fiap.framesnap.application.orders.usecases.CreateOrderUseCase;
import com.fiap.framesnap.application.orders.usecases.GetAllOrdersUseCase;
import com.fiap.framesnap.application.orders.usecases.GetOrderByIdUseCase;
import com.fiap.framesnap.application.orders.usecases.UpdateOrderStateUseCase;
import com.fiap.framesnap.entities.orders.Order;
import com.fiap.framesnap.infrastructure.orders.controller.dto.OrderRequest;
import com.fiap.framesnap.infrastructure.orders.controller.dto.OrderResponse;
import com.fiap.framesnap.infrastructure.orders.controller.dto.UpdateOrderStateRequest;
import com.fiap.framesnap.infrastructure.orders.controller.mapper.OrderDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderApi {

    private final CreateOrderUseCase createOrderUseCase;
    private final GetAllOrdersUseCase getAllOrdersUseCase;
    private final GetOrderByIdUseCase getOrderByIdUseCase;
    private final UpdateOrderStateUseCase updateOrderStateUseCase;
    private final OrderDTOMapper orderDTOMapper;

    @Autowired
    public OrderApi(CreateOrderUseCase createOrderUseCase,
                    GetAllOrdersUseCase getAllOrdersUseCase,
                    GetOrderByIdUseCase getOrderByIdUseCase,
                    UpdateOrderStateUseCase updateOrderStateUseCase,
                    OrderDTOMapper orderDTOMapper) {
        this.createOrderUseCase = createOrderUseCase;
        this.getAllOrdersUseCase = getAllOrdersUseCase;
        this.getOrderByIdUseCase = getOrderByIdUseCase;
        this.updateOrderStateUseCase = updateOrderStateUseCase;
        this.orderDTOMapper = orderDTOMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        Order order = orderDTOMapper.toOrder(orderRequest);
        Order createdOrder = createOrderUseCase.createOrder(order, orderRequest.getDocument());
        OrderResponse response = orderDTOMapper.toOrderResponse(createdOrder);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<Order> orders = getAllOrdersUseCase.getAll();
        List<OrderResponse> orderResponses = orders.stream()
                .map(orderDTOMapper::toOrderResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable UUID id) {
        return getOrderByIdUseCase.getOrderById(id)
                .map(order -> ResponseEntity.ok(orderDTOMapper.toOrderResponse(order)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/state")
    public ResponseEntity<OrderResponse> updateOrderState(@PathVariable UUID id, @RequestBody UpdateOrderStateRequest updateOrderStateRequest) {
        String newState = updateOrderStateRequest.getNewState();
        Order updatedOrder = updateOrderStateUseCase.updateOrderState(id, newState);
        OrderResponse response = orderDTOMapper.toOrderResponse(updatedOrder);
        return ResponseEntity.ok(response);
    }
}
