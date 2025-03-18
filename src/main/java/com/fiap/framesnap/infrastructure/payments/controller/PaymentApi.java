package com.fiap.framesnap.infrastructure.payments.controller;

import com.fiap.framesnap.application.payments.usecases.CreatePaymentUseCase;
import com.fiap.framesnap.entities.payments.Payment;
import com.fiap.framesnap.infrastructure.payments.controller.dto.CreatePaymentRequest;
import com.fiap.framesnap.infrastructure.payments.controller.dto.PaymentResponse;
import com.fiap.framesnap.infrastructure.payments.controller.mapper.PaymentDTOMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentApi {

    private final CreatePaymentUseCase createPaymentUseCase;
    private final PaymentDTOMapper paymentDTOMapper;

    public PaymentApi(CreatePaymentUseCase createPaymentUseCase, PaymentDTOMapper paymentDTOMapper) {
        this.createPaymentUseCase = createPaymentUseCase;
        this.paymentDTOMapper = paymentDTOMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody CreatePaymentRequest createPaymentRequestDTO) {
        Payment payment = createPaymentUseCase.createPayment(
                PaymentDTOMapper.toDomain(createPaymentRequestDTO));
        PaymentResponse paymentResponse = paymentDTOMapper.toPaymentResponse(payment);
        return ResponseEntity.ok(paymentResponse);
        }
}
