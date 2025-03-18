package com.fiap.framesnap.infrastructure.orders.entity;

import com.fiap.framesnap.entities.orders.OrderItem;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.math.BigDecimal;
import java.util.UUID;

@DynamoDbBean
public class OrderItemEntity {

    private UUID id;
    private UUID orderId;
    private UUID productId;
    private int quantity;
    private String observation;
    private BigDecimal totalPrice;

    public OrderItemEntity() {}


    public OrderItemEntity(OrderItem orderItem, UUID orderId) {
        this.id = orderItem.getId();
        this.productId = orderItem.getProductId();
        this.quantity = orderItem.getQuantity();
        this.observation = orderItem.getObservation();
        this.totalPrice = orderItem.getTotalPrice();
        this.orderId = orderId;
    }


    public OrderItemEntity(OrderItem orderItem) {
        this(orderItem, null);
    }

    public OrderItem toOrderItem(boolean fromDatabase) {
        if (fromDatabase) {
            return new OrderItem(this.id, this.productId, this.quantity, this.observation, this.totalPrice, true);
        } else {
            return new OrderItem(this.id, this.productId, this.quantity, this.observation, this.totalPrice, false);
        }
    }

    @DynamoDbPartitionKey
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
