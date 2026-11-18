package com.example.excpetion;

public class ServiceUnavailableException extends RuntimeException {

    public ServiceUnavailableException(String serviceName) {
        super("Сервис '" + serviceName + "' недоступен");
    }
}
