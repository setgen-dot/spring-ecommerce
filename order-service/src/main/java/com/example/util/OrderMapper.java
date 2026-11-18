package com.example.util;

import com.example.dto.response.CartItemResponse;
import com.example.dto.response.OrderResponse;
import com.example.entity.Order;
import com.example.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "orderItems", target = "items")
    OrderResponse toResponse(Order order);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    OrderItem toOrderItem(CartItemResponse cartItemResponse);
}
