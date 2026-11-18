package com.example.dto.response;

import com.example.entity.Order;
import com.example.entity.OrderItem;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        BigDecimal totalAmount,
        String shippingCity,
        String shippingStreet,
        String shippingHouse,
        String shippingApartment,
        Order.OrderStatus status,
        List<OrderItemResponse> items,
        Instant createdAt
) {}
