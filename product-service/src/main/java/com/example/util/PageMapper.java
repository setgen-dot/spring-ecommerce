package com.example.util;

import com.example.dto.response.PageResponse;
import com.example.dto.response.ProductResponse;
import com.example.entity.Product;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface PageMapper {

    ProductResponse toResponse(Product product);

    default PageResponse<ProductResponse> toResponse(Page<Product> page) {
        return new PageResponse<>(
                page.getContent().stream().map(this::toResponse).toList(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}
