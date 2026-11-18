package com.example.controller;

import com.example.dto.request.ProductRequest;
import com.example.dto.response.PageResponse;
import com.example.dto.response.ProductResponse;
import com.example.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    @Operation(summary = "Получить товар по ID")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") Long id) {
        ProductResponse productResponse = productService.getById(id);
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping
    @Operation(summary = "Получить страницу товаров")
    public ResponseEntity<PageResponse<ProductResponse>> getProductsByCategoryId(@RequestParam(name = "categoryId", required = false) Long categoryId,
                                                                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                                                                 @RequestParam(name = "size", defaultValue = "20") int size) {
        PageResponse<ProductResponse> pageResponse = productService.getProducts(categoryId, page, size);
        return ResponseEntity.ok(pageResponse);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Добавить товар")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody @Valid ProductRequest request) {
        ProductResponse productResponse = productService.create(request);
        return ResponseEntity.ok(productResponse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Обновить товар по ID")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("id") Long id, @RequestBody @Valid ProductRequest request) {
        ProductResponse productResponse = productService.update(id, request);
        return ResponseEntity.ok(productResponse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удалить товар по ID")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
