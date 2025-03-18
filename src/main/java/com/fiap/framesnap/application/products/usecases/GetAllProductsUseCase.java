package com.fiap.framesnap.application.products.usecases;

import com.fiap.framesnap.application.products.gateways.ProductGateway;
import com.fiap.framesnap.entities.products.Product;

import java.util.List;

public class GetAllProductsUseCase {

    private final ProductGateway productGateway;

    public GetAllProductsUseCase(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    public List<Product> getAll() {
        return productGateway.getAll();
    }
}
