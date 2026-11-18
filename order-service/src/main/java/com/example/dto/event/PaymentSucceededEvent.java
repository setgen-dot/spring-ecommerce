package com.example.dto.event;

public record PaymentSucceededEvent(Long paymentId, Long orderId) {}
