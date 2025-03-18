package com.fiap.framesnap.infrastructure.products.repository;

import com.fiap.framesnap.infrastructure.products.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaProductRepository extends JpaRepository<ProductEntity, UUID> {

    List<ProductEntity> findByCategory(String category);
}
