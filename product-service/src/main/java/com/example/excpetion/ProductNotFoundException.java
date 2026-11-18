package com.example.excpetion;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long id) {
        super("Товар с ID=" + id + " не найден");
    }
}
