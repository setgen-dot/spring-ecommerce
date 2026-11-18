package com.example.service;

import com.example.client.CartClient;
import com.example.dto.response.CartResponse;
import com.example.exception.ServiceUnavailableException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartClientService {

    private final CartClient cartClient;

    @Retry(name = "cart-service")
    @CircuitBreaker(name = "cart-service", fallbackMethod = "getCartFallback")
    public CartResponse getCart(Long userId) {
        return cartClient.getCartByUserId(userId);
    }

    private CartResponse getCartFallback(Long userId, Throwable ex) {
        log.warn("Cart service is unavailable: {}", ex.getMessage());
        throw new ServiceUnavailableException("cart-service");
    }
}
