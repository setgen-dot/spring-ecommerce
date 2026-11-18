package com.example.exception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(Long id) {
        super("Заказ с ID=" + id + " не найден");
    }
}
