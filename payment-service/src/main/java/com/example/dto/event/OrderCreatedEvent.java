package com.example.dto.event;

import java.math.BigDecimal;

public record OrderCreatedEvent(Long orderId, BigDecimal totalAmount, Long userId, Long cartId) {}
