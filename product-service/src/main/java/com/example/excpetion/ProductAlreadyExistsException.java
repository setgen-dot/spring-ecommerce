package com.example.excpetion;

public class ProductAlreadyExistsException extends RuntimeException {

    public ProductAlreadyExistsException(String name) {
        super("Товар с названием '" + name + "'уже существует");
    }
}
