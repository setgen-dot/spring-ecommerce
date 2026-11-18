package com.example.kafka.consumer;

import com.example.config.KafkaTopics;
import com.example.dto.event.PaymentSucceededEvent;
import com.example.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentEventConsumer {

    private final OrderService orderService;

    @KafkaListener(topics = KafkaTopics.PAYMENT_SUCCEED, groupId = "order-group")
    public void consumePaymentSucceedEvent(PaymentSucceededEvent event) {
        log.info("payment-succeed event consumed: {}", event);
        orderService.markAsPaid(event.orderId());
    }
}
