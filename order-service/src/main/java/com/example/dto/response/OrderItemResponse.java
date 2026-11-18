package com.example.dto.response;

import java.math.BigDecimal;

public record OrderItemResponse(Long productId, String productName, BigDecimal price, int quantity) {}
