package com.example.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Адрес эл. почты не может быть пустым")
        @Email(message = "Неправильный адрес эл.почты")
        String email,

        @NotBlank(message = "Пароль не может быть пустым")
        String password
) {}
