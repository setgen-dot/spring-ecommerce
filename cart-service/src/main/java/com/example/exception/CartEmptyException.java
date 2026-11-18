package com.example.exception;

public class CartEmptyException extends RuntimeException {

    public CartEmptyException() {
        super("Корзина пустая");
    }
}
