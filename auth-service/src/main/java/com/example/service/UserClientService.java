package com.example.service;

import com.example.client.UserClient;
import com.example.dto.request.CreateUserRequest;
import com.example.dto.response.UserCreatedResponse;
import com.example.exception.ServiceUnavailableException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserClientService {

    private final UserClient userClient;

    @Retry(name = "user-service")
    @CircuitBreaker(name = "user-service", fallbackMethod = "createUserFallback")
    public UserCreatedResponse createUser(CreateUserRequest request) {
        return userClient.createUser(request);
    }

    private UserCreatedResponse createUserFallback(CreateUserRequest request, Throwable ex) {
        log.warn("User service is unavailable: {}", ex.getMessage());
        throw new ServiceUnavailableException("user-service");
    }
}
