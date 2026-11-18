package com.example.kafka.producer;

import com.example.config.KafkaTopics;
import com.example.dto.event.PaymentSucceededEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendPaymentSucceed(PaymentSucceededEvent event) {
        kafkaTemplate.send(KafkaTopics.PAYMENT_SUCCEED, event);
        log.info("payment-paid event published: {}", event);
    }
}
