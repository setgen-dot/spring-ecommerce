package com.example.controller;

import com.example.dto.request.CreateUserRequest;
import com.example.dto.response.UserCreatedResponse;
import com.example.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/internal/users")
public class InternalUserController {

    private final UserService userService;

    @PostMapping
    public UserCreatedResponse createUser(@RequestBody @Valid CreateUserRequest request) {
        return new UserCreatedResponse(userService.create(request));
    }
}
