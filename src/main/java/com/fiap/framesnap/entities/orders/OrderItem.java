package com.fiap.framesnap.entities.orders;

import com.fiap.framesnap.entities.products.Product;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderItem {

    private UUID id;
    private Product product;
    private UUID productId;
    private int quantity;
    private String observation;
    private BigDecimal totalPrice;
    private BigDecimal price;

    public OrderItem(UUID id, UUID productId, int quantity, String observation) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.observation = observation;
        this.totalPrice = calculateTotalPrice();
    }

    public OrderItem(UUID productId, int quantity, String observation, BigDecimal price) {
        this.productId = productId;
        this.quantity = quantity;
        this.observation = observation;
        this.price = price;
        this.totalPrice = calculateTotalPrice();
    }

    public OrderItem(UUID id, Product product, int quantity, String observation) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.observation = observation;
        this.price = (product != null) ? product.getPrice() : null;
        this.totalPrice = calculateTotalPrice();
    }

    public OrderItem(UUID id, UUID productId, int quantity, String observation, BigDecimal price) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.observation = observation;
        this.price = price;
        this.totalPrice = calculateTotalPrice();
    }

    public OrderItem(UUID id, UUID productId, int quantity, String observation, BigDecimal priceOrTotal, boolean fromDatabase) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.observation = observation;

        if (fromDatabase) {
            this.totalPrice = priceOrTotal;
            this.price = priceOrTotal.divide(BigDecimal.valueOf(quantity), BigDecimal.ROUND_HALF_UP);
        } else {
            this.price = priceOrTotal;
            this.totalPrice = calculateTotalPrice();
        }
    }

    public UUID getId() {
        return id;
    }

    public UUID getProductId() {
        return productId;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getObservation() {
        return observation;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    private BigDecimal calculateTotalPrice() {
        if (this.totalPrice != null) {
            return this.totalPrice;
        }
        if (this.price != null) {
            return this.price.multiply(BigDecimal.valueOf(quantity));
        }
        return BigDecimal.ZERO;
    }


    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setProduct(Product product) {
        this.product = product;
        this.price = (product != null) ? product.getPrice() : null;
        this.totalPrice = calculateTotalPrice();
    }
}
