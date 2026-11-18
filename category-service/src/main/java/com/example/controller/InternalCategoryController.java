package com.example.controller;

import com.example.dto.response.CategoryExistsResponse;
import com.example.service.CategoryService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/internal/categories")
public class InternalCategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{id}/exists")
    public CategoryExistsResponse exists(@PathVariable Long id) {
        return categoryService.existsById(id);
    }
}
