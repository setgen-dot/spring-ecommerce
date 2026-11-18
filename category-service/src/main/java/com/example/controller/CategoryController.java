package com.example.controller;

import com.example.dto.request.CategoryRequest;
import com.example.dto.response.CategoryResponse;
import com.example.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{id}")
    @Operation(summary = "Получить категорию по ID")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable("id") Long id) {
        CategoryResponse categoryResponse = categoryService.getById(id);
        return ResponseEntity.ok(categoryResponse);
    }

    @GetMapping
    @Operation(summary = "Получить все категории")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categories = categoryService.getAll();
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Добавить категорию")
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody @Valid CategoryRequest request) {
        CategoryResponse categoryResponse = categoryService.create(request);
        return ResponseEntity.ok(categoryResponse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Обновить категорию по ID")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable("id") Long id, @RequestBody @Valid CategoryRequest request) {
        CategoryResponse response = categoryService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удалить категорию по ID")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
