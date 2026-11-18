package com.example.exception;

public class PaymentNotFoundException extends RuntimeException {

    public PaymentNotFoundException(Long id) {
        super("Запрос на оплату с ID=" + id + " не найден");
    }
}
