package com.example.kafka.consumer;

import com.example.config.KafkaTopics;
import com.example.dto.event.OrderCreatedEvent;
import com.example.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final CartService cartService;

    @KafkaListener(topics = KafkaTopics.ORDER_CREATED, groupId = "cart-group")
    public void consumeOrderCreatedEvent(OrderCreatedEvent event) {
        log.info("order-created event consumed: {}", event);
        cartService.clearCartById(event.cartId());
    }
}
