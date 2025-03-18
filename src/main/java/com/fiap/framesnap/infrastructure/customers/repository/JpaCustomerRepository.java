package com.fiap.framesnap.infrastructure.customers.repository;

import com.fiap.framesnap.infrastructure.customers.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface JpaCustomerRepository extends JpaRepository<CustomerEntity, UUID> {

    @Query("SELECT c FROM CustomerEntity c WHERE c.document = :document")
    Optional<CustomerEntity> findCustomerByDocument(@Param("document") String document);

    @Query("SELECT c FROM CustomerEntity c WHERE c.id = :id")
    Optional<CustomerEntity> findCustomerById(@Param("id") UUID id);
}
