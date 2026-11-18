package com.example.controller;

import com.example.dto.request.CartItemQuantityRequest;
import com.example.dto.request.CartItemRequest;
import com.example.dto.response.CartResponse;
import com.example.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/carts")
public class CartController {

    private final CartService cartService;

    @GetMapping
    @Operation(summary = "Получить текущюю корзину пользователя")
    public ResponseEntity<CartResponse> getCart(@AuthenticationPrincipal Jwt jwt) {
        CartResponse cartResponse = cartService.getCart(Long.valueOf(jwt.getSubject()));
        return ResponseEntity.ok(cartResponse);
    }

    @PostMapping("/items")
    @Operation(summary = "Добавить товар в коризну")
    public ResponseEntity<CartResponse> addItem(@RequestBody @Valid CartItemRequest request, @AuthenticationPrincipal Jwt jwt) {
        CartResponse cartResponse = cartService.addItem(request, Long.valueOf(jwt.getSubject()));
        return ResponseEntity.ok(cartResponse);
    }

    @PutMapping("/items/{id}")
    @Operation(summary = "Изменить количество товара в корзине")
    public ResponseEntity<CartResponse> updateItemQuantity(@PathVariable("id") Long cartItemId, @RequestBody @Valid CartItemQuantityRequest request, @AuthenticationPrincipal Jwt jwt) {
        CartResponse cartResponse = cartService.updateItemQuantity(cartItemId, request, Long.valueOf(jwt.getSubject()));
        return ResponseEntity.ok(cartResponse);
    }

    @DeleteMapping("/items/{id}")
    @Operation(summary = "Удалить товар из корзины")
    public ResponseEntity<CartResponse> removeItem(@PathVariable("id") Long cartItemId, @AuthenticationPrincipal Jwt jwt) {
        CartResponse cartResponse = cartService.removeItem(cartItemId, Long.valueOf(jwt.getSubject()));
        return ResponseEntity.ok(cartResponse);
    }

    @DeleteMapping
    @Operation(summary = "Очистить корзину")
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal Jwt jwt) {
        cartService.clearCartByUserId(Long.valueOf(jwt.getSubject()));
        return ResponseEntity.noContent().build();
    }
}
