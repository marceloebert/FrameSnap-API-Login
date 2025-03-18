package com.fiap.framesnap.infrastructure.payments.controller;

import com.fiap.framesnap.application.payments.usecases.ApprovePaymentUseCase;
import com.fiap.framesnap.entities.payments.Payment;
import com.fiap.framesnap.infrastructure.payments.controller.dto.PaymentApprovalRequest;
import com.fiap.framesnap.infrastructure.payments.controller.dto.PaymentResponse;
import com.fiap.framesnap.infrastructure.payments.controller.mapper.PaymentDTOMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentWebhookApi {

    private final ApprovePaymentUseCase approvePaymentUseCase;
    private final PaymentDTOMapper paymentDTOMapper;

    public PaymentWebhookApi(ApprovePaymentUseCase approvePaymentUseCase, PaymentDTOMapper paymentDTOMapper) {
        this.approvePaymentUseCase = approvePaymentUseCase;
        this.paymentDTOMapper = paymentDTOMapper;
    }

    @PostMapping("/approve")
    public ResponseEntity<PaymentResponse> approvePayment(@RequestBody PaymentApprovalRequest paymentApprovalRequestDTO) {
        Payment payment = approvePaymentUseCase.approvePayment(
                paymentApprovalRequestDTO.getPaymentId(),
                paymentApprovalRequestDTO.getTransactionId(),
                paymentApprovalRequestDTO.getAmount());

        PaymentResponse paymentResponse = paymentDTOMapper.toPaymentResponse(payment);
        return ResponseEntity.ok(paymentResponse);
    }
}
