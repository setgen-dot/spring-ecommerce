package com.example.exception;

public class ServiceUnavailableException extends RuntimeException {

    public ServiceUnavailableException(String serviceName) {
        super("Сервис '" + serviceName + "' недоступен");
    }
}
