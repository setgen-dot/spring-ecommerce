package com.example.controller;

import com.example.dto.response.CartResponse;
import com.example.service.CartService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/internal/carts")
public class InternalCartController {

    private final CartService cartService;

    @GetMapping("/user/{userId}")
    public CartResponse getCartByUserId(@PathVariable("userId") Long userId) {
        return cartService.getCart(userId);
    }
}
