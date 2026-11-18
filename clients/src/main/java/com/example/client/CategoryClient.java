package com.example.client;

import com.example.dto.response.CategoryExistsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "category-service", path = "/api/v1/internal/categories")
public interface CategoryClient {

    @GetMapping("/{id}/exists")
    CategoryExistsResponse exists(@PathVariable("id") Long id);
}
