package com.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Setter
@Getter
@Entity
@Builder
@Table(name = "payments")
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long orderId;

    private BigDecimal amount;

    private PaymentStatus status;

    private Instant createdAt;

    private Instant paidAt;

    public enum PaymentStatus {
        PENDING, SUCCESS, CANCELLED
    }
}
