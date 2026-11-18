package com.example.controller;

import com.example.dto.response.PaymentResponse;
import com.example.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/{orderId}/pay")
    @Operation(summary = "Оплатить заказ")
    public ResponseEntity<PaymentResponse> pay(@PathVariable("orderId") Long orderId, @AuthenticationPrincipal Jwt jwt) {
        PaymentResponse paymentResponse = paymentService.pay(orderId, Long.valueOf(jwt.getSubject()));
        return ResponseEntity.ok(paymentResponse);
    }
}
