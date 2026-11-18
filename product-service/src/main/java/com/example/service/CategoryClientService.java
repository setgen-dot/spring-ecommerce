package com.example.service;

import com.example.client.CategoryClient;
import com.example.dto.response.CartResponse;
import com.example.dto.response.CategoryExistsResponse;
import com.example.excpetion.ServiceUnavailableException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryClientService {

    private final CategoryClient categoryClient;

    @Retry(name = "category-service")
    @CircuitBreaker(name = "category-service", fallbackMethod = "categoryExistsFallback")
    public CategoryExistsResponse exists(Long categoryId) {
        return categoryClient.exists(categoryId);
    }

    private CategoryExistsResponse categoryExistsFallback(Long categoryId, Throwable ex) {
        log.warn("Category service is unavailable: {}", ex.getMessage());
        throw new ServiceUnavailableException("category-service");
    }
}
