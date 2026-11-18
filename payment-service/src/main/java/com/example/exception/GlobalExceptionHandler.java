package com.example.exception;

import com.example.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePaymentNotFoundException(PaymentNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(
                "PAYMENT_NOT_FOUND",
                ex.getMessage()
        ));
    }

    @ExceptionHandler(PaymentCannotBePaidException.class)
    public ResponseEntity<ErrorResponse> handlePaymentCannotBePaidException(PaymentCannotBePaidException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(
                "PAYMENT_CANNOT_BE_PAID",
                ex.getMessage()
        ));
    }
}
