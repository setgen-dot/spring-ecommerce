package com.example.exception;

public class EmailAlreadyTakenException extends RuntimeException {

    public EmailAlreadyTakenException(String email) {
        super("Адрес эл. почты '" + email + "' уже используется");
    }
}
