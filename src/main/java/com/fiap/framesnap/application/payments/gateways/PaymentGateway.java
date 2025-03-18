package com.fiap.framesnap.application.payments.gateways;

import com.fiap.framesnap.entities.payments.Payment;

import java.util.Optional;
import java.util.UUID;

public interface PaymentGateway {
    Payment save(Payment payment);

    Optional<Payment> findById(UUID id);
}
