package com.example.dto.response;

import com.example.entity.Payment;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentResponse(
        Long id,
        Long orderId,
        BigDecimal amount,
        Payment.PaymentStatus status,
        Instant createdAt,
        Instant paidAt
) {}
