package com.example.exception;

public class CartItemNotFoundException extends RuntimeException {
    public CartItemNotFoundException(Long id) {
        super("Товар с ID=" + id + " в корзине не найден");
    }
}
