package com.example.service;

import com.example.client.ProductClient;
import com.example.dto.response.ProductResponse;
import com.example.exception.ProductNotFoundException;
import com.example.exception.ServiceUnavailableException;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductClientService {

    private final ProductClient productClient;

    @Retry(name = "product-service")
    @CircuitBreaker(name = "product-service", fallbackMethod = "getProductByIdFallback")
    public ProductResponse getProductById(Long productId) {
        try {
            return productClient.getProductById(productId);
        } catch (FeignException.NotFound e) {
            throw new ProductNotFoundException(productId);
        }
    }

    private ProductResponse getProductByIdFallback(Long productId, Throwable ex) {
        log.warn("Product service is unavailable: {}", ex.getMessage());
        throw new ServiceUnavailableException("product-service");
    }
}
