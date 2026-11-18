package com.example.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CartItemQuantityRequest(
        @NotNull(message = "Количество товара обязательное поле")
        @Min(value = 0, message = "Минимальное количество товара  — 0 шт.")
        @Max(value = 1000, message = "Максимальное количество товара  — 1 000 шт.")
        int quantity
) {}
