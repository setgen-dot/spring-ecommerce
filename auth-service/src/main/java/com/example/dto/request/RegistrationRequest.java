package com.example.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistrationRequest(
        @Size(min = 1, max = 100, message = "Длина имени должна быть от 1 до 100 символов")
        @NotBlank(message = "Имя не может быть пустым")
        String firstname,

        @Size(min = 1, max = 100, message = "Длина фамилии должна быть от 1 до 100 символов")
        @NotBlank(message = "Фамилия не может быть пустой")
        String lastname,

        @NotBlank(message = "Адрес эл. почты не может быть пустым")
        @Email(message = "Неправильный адрес эл.почты")
        String email,

        @NotBlank(message = "Пароль не может быть пустым")
        @Size(min = 6, max = 100, message = "Длина пароля должна быть от 6 до 100 символов")
        String password
) {}
