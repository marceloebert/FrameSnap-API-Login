package com.fiap.framesnap.infrastructure.products.controller.mapper;

import com.fiap.framesnap.entities.products.Product;
import com.fiap.framesnap.infrastructure.products.controller.dto.ProductRequest;
import com.fiap.framesnap.infrastructure.products.controller.dto.ProductResponse;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProductDTOMapper {

    public Product toProductForCreation(ProductRequest productRequest) {
        return new Product(
                UUID.randomUUID(),
                productRequest.getDescription(),
                productRequest.getPrice(),
                com.fiap.framesnap.entities.products.enums.Category.valueOf(productRequest.getCategory())
        );
    }

    public Product toProductForUpdate(ProductRequest productRequest, UUID id) {
        return new Product(
                id,
                productRequest.getDescription(),
                productRequest.getPrice(),
                com.fiap.framesnap.entities.products.enums.Category.valueOf(productRequest.getCategory())
        );
    }

    public ProductResponse toProductResponse(Product product) {
        ProductResponse dto = new ProductResponse();
        dto.setId(product.getId());
        dto.setCategory(product.getCategory());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        return dto;
    }
}
