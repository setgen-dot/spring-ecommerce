package com.example.client;

import com.example.dto.response.CartResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cart-service", path = "/api/v1/internal/carts")
public interface CartClient {

    @GetMapping("/user/{userId}")
    CartResponse getCartByUserId(@PathVariable("userId") Long userId);
}
