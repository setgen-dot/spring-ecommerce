package com.example.dto.response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public record CartResponse(BigDecimal totalAmount, List<CartItemResponse> items) {

    public CartResponse(BigDecimal totalAmount) {
        this(totalAmount, new ArrayList<>());
    }
}
