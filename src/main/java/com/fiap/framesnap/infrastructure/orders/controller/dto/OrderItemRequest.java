package com.fiap.framesnap.infrastructure.orders.controller.dto;

import com.fiap.framesnap.entities.products.Product;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderItemRequest {


    private UUID productId;
    private int quantity;
    private String observation;
    private Product product;
    private BigDecimal price;


    public UUID getProductId() {
        return productId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
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
}
