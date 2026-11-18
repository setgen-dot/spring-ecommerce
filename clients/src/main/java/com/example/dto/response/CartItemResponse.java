package com.example.dto.response;

import java.math.BigDecimal;

public record CartItemResponse(Long id, Long productId, BigDecimal price, int quantity) {}
