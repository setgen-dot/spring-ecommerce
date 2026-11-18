package com.example.exception;

import com.example.entity.Payment;

public class PaymentCannotBePaidException extends RuntimeException {
    public PaymentCannotBePaidException(Long id, Payment.PaymentStatus status) {
        super("Платеж с ID="+ id + " не может быть оплачен. Статус платежа — " + status);
    }
}
