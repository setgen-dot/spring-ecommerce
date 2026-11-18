package com.example.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record OrderRequest(
        @NotBlank(message = "Поле обязательно для заполнения")
        @Size(min = 1, max = 40, message = "Длина названия города от 1 до 40 символов")
        String shippingCity,

        @NotBlank(message = "Поле обязательно для заполнения")
        @Size(min = 1, max = 40, message = "Длина названия улицы от 1 до 40 символов")
        String shippingStreet,

        @NotBlank(message = "Поле обязательно для заполнения")
        @Size(min = 1, max = 40, message = "Длина номера дома от 1 до 40 символов")
        String shippingHouse,

        @NotBlank(message = "Поле обязательно для заполнения")
        @Size(min = 1, max = 40, message = "Длина номера квартиры от 1 до 40 символов")
        String shippingApartment
) {}
