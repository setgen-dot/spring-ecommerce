package com.example.contoller;

import com.example.dto.response.OrderRequest;
import com.example.dto.response.OrderResponse;
import com.example.dto.response.PageResponse;
import com.example.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Получить заказ по ID")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable("id") Long id) {
        OrderResponse orderResponse = orderService.getById(id);
        return ResponseEntity.ok(orderResponse);
    }

    @GetMapping("/my/{id}")
    @Operation(summary = "Получить свой заказ по ID")
    public ResponseEntity<OrderResponse> getMyOrderById(@PathVariable("id") Long id, @AuthenticationPrincipal Jwt jwt) {
        OrderResponse orderResponse = orderService.getByIdAndUserId(id, Long.valueOf(jwt.getSubject()));
        return ResponseEntity.ok(orderResponse);
    }

    @GetMapping("/my")
    @Operation(summary = "Получить страницу своих заказов")
    public ResponseEntity<PageResponse<OrderResponse>> getMyOrders(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                   @RequestParam(name = "size", defaultValue = "20") int size,
                                                                   @AuthenticationPrincipal Jwt jwt) {
        PageResponse<OrderResponse> pageResponse = orderService.getOrdersByUserId(Long.valueOf(jwt.getSubject()), page, size);
        return ResponseEntity.ok(pageResponse);
    }

    @PostMapping
    @Operation(summary = "Создать заказ")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid OrderRequest request, @AuthenticationPrincipal Jwt jwt) {
        OrderResponse orderResponse = orderService.add(request, Long.valueOf(jwt.getSubject()));
        return ResponseEntity.ok(orderResponse);
    }
}
