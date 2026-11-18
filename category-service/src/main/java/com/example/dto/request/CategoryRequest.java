package com.example.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
        @NotBlank(message = "Название категории не может быть пустым")
        @Size(min = 1, max = 50, message = "Длина названия категории от 1 до 50 символов")
        String name
) {}
