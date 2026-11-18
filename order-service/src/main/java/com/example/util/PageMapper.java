package com.example.util;

import com.example.dto.response.OrderResponse;
import com.example.dto.response.PageResponse;
import com.example.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface PageMapper {

    @Mapping(source = "orderItems", target = "items")
    OrderResponse toResponse(Order order);

    default PageResponse<OrderResponse> toResponse(Page<Order> page) {
        return new PageResponse<>(
                page.getContent().stream().map(this::toResponse).toList(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}
