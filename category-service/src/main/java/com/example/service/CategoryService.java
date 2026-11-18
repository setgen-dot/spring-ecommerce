package com.example.service;

import com.example.dto.response.CategoryExistsResponse;
import com.example.dto.response.CategoryResponse;
import com.example.exception.CategoryNotFoundException;
import com.example.dto.request.CategoryRequest;
import com.example.exception.CategoryAlreadyExistsException;
import com.example.entity.Category;
import com.example.repository.CategoryRepository;
import com.example.util.CategoryMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Cacheable(value = "categories", key = "#id")
    public CategoryResponse getById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
        return categoryMapper.toResponse(category);
    }

    @Cacheable("all-categories")
    public List<CategoryResponse> getAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(categoryMapper::toResponse).toList();
    }

    @Transactional
    @Caching(put = @CachePut(value = "categories", key = "#result.id"),
            evict = @CacheEvict(value = "all-categories", allEntries = true)
    )
    public CategoryResponse create(CategoryRequest request) {
        if (categoryRepository.existsByName(request.name())) {
            throw new CategoryAlreadyExistsException(request.name());
        }

        Category category = Category.builder().name(request.name()).build();
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toResponse(savedCategory);
    }

    @Transactional
    @Caching(put = @CachePut(value = "categories", key = "#id"),
             evict = @CacheEvict(value = "all-categories", allEntries = true)
    )
    public CategoryResponse update(Long id, @Valid CategoryRequest request) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
        category.setName(request.name());
        return categoryMapper.toResponse(category);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "categories", key = "#id"),
            @CacheEvict(value = "all-categories", allEntries = true)
    })
    public void deleteById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
        categoryRepository.delete(category);
    }

    public CategoryExistsResponse existsById(Long id) {
        boolean exists = categoryRepository.existsById(id);
        return new CategoryExistsResponse(id, exists);
    }
}
