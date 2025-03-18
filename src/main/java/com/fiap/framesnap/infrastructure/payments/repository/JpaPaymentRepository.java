package com.fiap.framesnap.infrastructure.payments.repository;

import com.fiap.framesnap.infrastructure.payments.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaPaymentRepository extends JpaRepository<PaymentEntity, UUID> {
}
