package com.example.client;

import com.example.dto.request.CreateUserRequest;
import com.example.dto.response.UserCreatedResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "user-service", path = "/api/v1/internal/users")
public interface UserClient {

    @PostMapping
    UserCreatedResponse createUser(CreateUserRequest request);
}
