package com.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Setter
@Getter
@Entity
@Builder
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private BigDecimal totalAmount;

    private String shippingCity;

    private String shippingStreet;

    private String shippingHouse;

    private String shippingApartment;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Instant createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    public enum OrderStatus {
        CREATED, PAID, CANCELLED, DELIVERED
    }
}
