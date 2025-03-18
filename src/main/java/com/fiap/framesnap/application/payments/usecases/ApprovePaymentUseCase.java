package com.fiap.framesnap.application.payments.usecases;

import com.fiap.framesnap.application.orders.usecases.UpdateOrderStateUseCase;
import com.fiap.framesnap.entities.payments.Payment;
import com.fiap.framesnap.application.payments.gateways.PaymentGateway;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class ApprovePaymentUseCase {

    private final PaymentGateway paymentGateway;
    private final UpdateOrderStateUseCase updateOrderStateUseCase;

    public ApprovePaymentUseCase(PaymentGateway paymentGateway,UpdateOrderStateUseCase updateOrderStateUseCase) {
        this.paymentGateway = paymentGateway;
        this.updateOrderStateUseCase = updateOrderStateUseCase;
    }

    public Payment approvePayment(UUID paymentId, String transactionId, BigDecimal amount) {
        Optional<Payment> optionalPayment = paymentGateway.findById(paymentId);

        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();
            if (payment.isPending()) {
                payment.setAmount(amount);
                payment.approve(transactionId);
                updateOrderStateUseCase.updateOrderState(payment.getOrderId(), "RECEIVED");
                return paymentGateway.save(payment);
            } else {
                throw new IllegalStateException("Payment is not pending");
            }
        } else {
            throw new IllegalArgumentException("Payment not found");
        }
    }
}
