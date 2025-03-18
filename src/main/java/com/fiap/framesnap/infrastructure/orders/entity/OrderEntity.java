package com.fiap.framesnap.infrastructure.orders.entity;

import com.fiap.framesnap.entities.orders.Order;
import com.fiap.framesnap.entities.customers.Customer;
import com.fiap.framesnap.entities.orders.OrderItem;
import com.fiap.framesnap.entities.orders.enums.OrderState;
import com.fiap.framesnap.entities.orders.enums.PaymentConfirmationStatus;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@DynamoDbBean
public class OrderEntity {

    private UUID id;
    private UUID customerId;
    private List<OrderItemEntity> items;
    private OrderState state;
    private BigDecimal totalPrice;
    private PaymentConfirmationStatus paymentConfirmationStatus;
    private String paymentId;
    private LocalDateTime creationTime;

    public OrderEntity() {}

    public OrderEntity(Order order) {
        this.id = order.getId();
        this.customerId = order.getCustomer().getId();
        this.state = order.getState();
        this.totalPrice = order.getTotalPrice();
        this.paymentId = order.getPayment() != null ? order.getPayment().getId().toString() : null;
        this.creationTime = order.getCreationTime();
        this.items = order.getItems().stream()
                .map(item -> new OrderItemEntity(item))
                .toList();
    }

    public Order toOrder() {
        List<OrderItem> orderItems = this.items.stream()
                .map(item -> item.toOrderItem(true))
                .toList();

        return new Order(
                this.id,
                null,
                orderItems,
                this.state,
                this.totalPrice,
                this.paymentConfirmationStatus,
                null, // O mesmo para PaymentEntity
                this.creationTime
        );
    }

    public Order toOrder(Customer customer) {
        List<OrderItem> orderItems = this.items.stream()
                .map(item -> item.toOrderItem(true))
                .toList();

        return new Order(
                this.id,
                customer, // ✅ Agora recebe o customer corretamente
                orderItems,
                this.state,
                this.totalPrice,
                this.paymentConfirmationStatus,
                null, // O mesmo para PaymentEntity
                this.creationTime
        );
    }

    @DynamoDbPartitionKey
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public List<OrderItemEntity> getItems() {
        return items;
    }

    public void setItems(List<OrderItemEntity> items) {
        this.items = items;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public PaymentConfirmationStatus getPaymentConfirmationStatus() {
        return paymentConfirmationStatus;
    }

    public void setPaymentConfirmationStatus(PaymentConfirmationStatus paymentConfirmationStatus) {
        this.paymentConfirmationStatus = paymentConfirmationStatus;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }
}
