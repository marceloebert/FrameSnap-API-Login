package com.fiap.framesnap.application.products.usecases;

import com.fiap.framesnap.application.products.gateways.ProductGateway;
import com.fiap.framesnap.entities.products.Product;

public class CreateProductUseCase {

    private final ProductGateway productGateway;

    public CreateProductUseCase(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    public Product createProduct(Product product) {
        return productGateway.create(product);
    }
}
