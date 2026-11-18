package com.example.client;

import com.example.dto.response.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", path = "/api/v1/internal/products")
public interface ProductClient {

    @GetMapping("/{id}")
    ProductResponse getProductById(@PathVariable("id") Long id);
}
