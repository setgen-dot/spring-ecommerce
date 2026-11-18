package com.example.util;

import com.example.dto.response.CartItemResponse;
import com.example.dto.response.CartResponse;
import com.example.dto.response.ProductResponse;
import com.example.entity.Cart;
import com.example.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(source = "cartItems", target = "items")
    CartResponse toResponse(Cart cart);

    @Mapping(source = "item.id", target = "id")
    @Mapping(source = "item.price", target = "price")
    CartItemResponse toCartItemResponse(CartItem item, ProductResponse product);
}
