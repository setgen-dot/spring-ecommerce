package com.example.dto.response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public record CartResponse(Long id, BigDecimal totalAmount, List<CartItemResponse> items) {}
