package com.example.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message = "Название товара не может быть пустым")
        @Size(min = 1, max = 50, message = "Длина названия товара от 1 до 50 символов")
        String name,

        @NotNull(message = "Цена товара обязательна")
        @DecimalMin(value = "1.00", message = "Цена товара должна быть не меньше 1.00 рубля")
        @DecimalMax(value = "1000000.00", message = "Цена товара не должна превышать 1 000 000.00 рублей")
        @Digits(integer = 7, fraction = 2, message = "Цена должна иметь максимум 2 знака после запятой")
        BigDecimal price,

        @NotNull(message = "ID категории обязательно")
        Long categoryId
) {}
