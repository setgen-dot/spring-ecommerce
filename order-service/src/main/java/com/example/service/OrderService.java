package com.example.service;

import com.example.dto.event.OrderCreatedEvent;
import com.example.dto.response.CartResponse;
import com.example.dto.response.OrderRequest;
import com.example.dto.response.OrderResponse;
import com.example.dto.response.PageResponse;
import com.example.entity.Order;
import com.example.entity.OrderItem;
import com.example.exception.CartEmptyException;
import com.example.exception.OrderNotFoundException;
import com.example.kafka.producer.OrderEventProducer;
import com.example.repository.OrderRepository;
import com.example.util.OrderMapper;
import com.example.util.PageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper mapper;
    private final CartClientService cartClientService;
    private final OrderEventProducer orderEventProducer;
    private final PageMapper pageMapper;

    public OrderResponse getById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        return mapper.toResponse(order);
    }

    public OrderResponse getByIdAndUserId(Long id, Long userId) {
        Order order = orderRepository.findByIdAndUserId(id, userId).orElseThrow(() -> new OrderNotFoundException(id));
        return mapper.toResponse(order);
    }

    public PageResponse<OrderResponse> getOrdersByUserId(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orderPage = orderRepository.findAllByUserIdOrderByCreatedAtDesc(userId, pageable);
        return pageMapper.toResponse(orderPage);
    }

    @Transactional
    public OrderResponse add(OrderRequest request, Long userId) {
        CartResponse cart = cartClientService.getCart(userId);
        if (cart.id() == null || cart.items().isEmpty()) {
            throw new CartEmptyException();
        }

        Order orderToSave = Order.builder()
                .userId(userId)
                .totalAmount(cart.totalAmount())
                .shippingCity(request.shippingCity())
                .shippingStreet(request.shippingStreet())
                .shippingHouse(request.shippingHouse())
                .shippingApartment(request.shippingApartment())
                .status(Order.OrderStatus.CREATED)
                .createdAt(Instant.now())
                .build();

        List<OrderItem> orderItems = cart.items().stream().map(mapper::toOrderItem).toList();
        orderItems.forEach(item -> item.setOrder(orderToSave));
        orderToSave.setOrderItems(orderItems);
        Order order = orderRepository.save(orderToSave);

        orderEventProducer.sendOrderCreated(new OrderCreatedEvent(order.getId(), order.getTotalAmount(), userId, cart.id()));

        return mapper.toResponse(order);
    }

    @Transactional
    public void markAsPaid(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        order.setStatus(Order.OrderStatus.PAID);
    }
}
