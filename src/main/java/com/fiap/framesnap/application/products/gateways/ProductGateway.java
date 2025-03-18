package com.fiap.framesnap.application.products.gateways;

import com.fiap.framesnap.entities.products.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductGateway {
    Product create(Product product);
    List<Product> getAll();
    Optional<Product> getById(UUID id);
    List<Product> getByCategory(String category);
    void remove(UUID id);
    Product update(Product product);
}
