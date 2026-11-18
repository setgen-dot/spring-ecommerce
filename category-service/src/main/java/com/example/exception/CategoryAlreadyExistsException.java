package com.example.exception;

public class CategoryAlreadyExistsException extends RuntimeException {

    public CategoryAlreadyExistsException(String name) {
        super("Категория с названием '" + name + "' уже существует");
    }
}
