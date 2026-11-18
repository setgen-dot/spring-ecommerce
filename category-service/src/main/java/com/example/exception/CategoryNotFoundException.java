package com.example.exception;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(Long id) {
        super("Категория с ID=" + id + " не найдена");
    }
}
