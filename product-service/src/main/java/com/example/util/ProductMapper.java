package com.example.util;

import com.example.dto.response.ProductResponse;
import com.example.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponse toResponse(Product product);
}
