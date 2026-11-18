package com.example.service;

import com.example.dto.response.ProductResponse;
import com.example.dto.request.ProductRequest;
import com.example.dto.response.PageResponse;
import com.example.excpetion.CategoryNotFoundException;
import com.example.excpetion.ProductAlreadyExistsException;
import com.example.entity.Product;
import com.example.excpetion.ProductNotFoundException;
import com.example.repository.ProductRepository;
import com.example.util.PageMapper;
import com.example.util.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductMapper productMapper;
    private final PageMapper pageMapper;
    private final ProductRepository productRepository;
    private final CategoryClientService categoryClientService;

    @Cacheable(value = "products", key = "#id")
    public ProductResponse getById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return productMapper.toResponse(product);
    }

    public PageResponse<ProductResponse> getProducts(Long categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage;

        if (categoryId == null) {
            productPage = productRepository.findAll(pageable);
        } else {
            productPage = productRepository.findAllByCategoryId(categoryId, pageable);
        }

        return pageMapper.toResponse(productPage);
    }

    @Transactional
    @CachePut(value = "products", key = "#result.id")
    public ProductResponse create(ProductRequest request) {
        if (productRepository.existsByName(request.name())) {
            throw new ProductAlreadyExistsException(request.name());
        }
        if (!categoryClientService.exists(request.categoryId()).exists()) {
            throw new CategoryNotFoundException(request.categoryId());
        }

        Product product = Product.builder()
                .name(request.name())
                .price(request.price())
                .categoryId(request.categoryId())
                .build();
        Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    @Transactional
    @CachePut(value = "products", key = "#id")
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        if (!product.getCategoryId().equals(request.categoryId())) {
            if (!categoryClientService.exists(request.categoryId()).exists()) {
                throw new CategoryNotFoundException(request.categoryId());
            }
        }

        product.setName(request.name());
        product.setPrice(request.price());
        product.setCategoryId(request.categoryId());
        return productMapper.toResponse(product);
    }

    @Transactional
    @CacheEvict(value = "products", key = "#id")
    public void deleteById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(product);
    }
}
