package com.example.controller;

import com.example.dto.request.LoginRequest;
import com.example.dto.request.RegistrationRequest;
import com.example.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Логин")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    @Operation(summary = "Регистрация")
    public ResponseEntity<String> register(@RequestBody @Valid RegistrationRequest request) {
        String token = authService.register(request);
        return ResponseEntity.ok(token);
    }
}
