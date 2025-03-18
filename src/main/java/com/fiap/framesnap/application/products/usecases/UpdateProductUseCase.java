package com.fiap.framesnap.application.products.usecases;

import com.fiap.framesnap.application.products.gateways.ProductGateway;
import com.fiap.framesnap.entities.products.Product;

public class UpdateProductUseCase {

    private final ProductGateway productGateway;

    public UpdateProductUseCase(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    public Product update(Product product) {
        return productGateway.update(product);
    }
}
