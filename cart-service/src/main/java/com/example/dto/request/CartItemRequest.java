package com.example.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CartItemRequest(
        @NotNull(message = "ID товара обязательное поле")
        Long productId,

        @Min(value = 1, message = "Минимальное количество товара — 1 шт.")
        int quantity
) {}
