package com.example.util;

import com.example.dto.response.CategoryResponse;
import com.example.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponse toResponse(Category category);
}
