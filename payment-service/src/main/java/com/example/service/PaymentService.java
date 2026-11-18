package com.example.service;

import com.example.dto.event.OrderCreatedEvent;
import com.example.dto.event.PaymentSucceededEvent;
import com.example.dto.response.PaymentResponse;
import com.example.entity.Payment;
import com.example.exception.PaymentCannotBePaidException;
import com.example.exception.PaymentNotFoundException;
import com.example.kafka.producer.PaymentEventProducer;
import com.example.repository.PaymentRepository;
import com.example.util.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final PaymentEventProducer paymentEventProducer;

    @Transactional
    public void add(OrderCreatedEvent event) {
        Payment payment = Payment.builder()
                .userId(event.userId())
                .orderId(event.orderId())
                .amount(event.totalAmount())
                .status(Payment.PaymentStatus.PENDING)
                .createdAt(Instant.now())
                .build();

        paymentRepository.save(payment);
    }

    @Transactional
    public PaymentResponse pay(Long id, Long userId) {
        Payment payment = paymentRepository.findByOrderIdAndUserId(id, userId).orElseThrow(() -> new PaymentNotFoundException(id));
        if (payment.getStatus() != Payment.PaymentStatus.PENDING) {
            throw new PaymentCannotBePaidException(id, payment.getStatus());
        }
        payment.setStatus(Payment.PaymentStatus.SUCCESS);
        payment.setPaidAt(Instant.now());

        paymentEventProducer.sendPaymentSucceed(new PaymentSucceededEvent(payment.getId(), payment.getOrderId()));
        return paymentMapper.toResponse(payment);
    }
}
