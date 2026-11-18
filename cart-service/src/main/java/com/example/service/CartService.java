package com.example.service;

import com.example.dto.request.CartItemQuantityRequest;
import com.example.dto.request.CartItemRequest;
import com.example.dto.response.CartItemResponse;
import com.example.dto.response.CartResponse;
import com.example.dto.response.ProductResponse;
import com.example.entity.Cart;
import com.example.entity.CartItem;
import com.example.exception.CartEmptyException;
import com.example.exception.CartItemNotFoundException;
import com.example.exception.ProductNotFoundException;
import com.example.repository.CartItemRepository;
import com.example.repository.CartRepository;
import com.example.util.CartMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductClientService productClientService;
    private final CartMapper cartMapper;

    public CartResponse getCart(Long userId) {
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        if (optionalCart.isEmpty()) return new CartResponse(null, BigDecimal.ZERO, new ArrayList<>());

        return buildCartResponse(optionalCart.get());
    }

    @Transactional
    public CartResponse addItem(CartItemRequest request, Long userId) {
        ProductResponse product = productClientService.getProductById(request.productId());

        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = new Cart(userId, BigDecimal.ZERO, new ArrayList<>());
            return cartRepository.save(newCart);
        });


        if (cart.getCartItems().stream().anyMatch(i -> i.getProductId().equals(request.productId()))) {
            CartItem item = cartItemRepository.findByCartAndProductId(cart, request.productId());
            item.setQuantity(item.getQuantity() + request.quantity());
        } else {
            CartItem item = CartItem.builder()
                    .productId(product.id())
                    .quantity(request.quantity())
                    .price(product.price())
                    .cart(cart)
                    .build();
            cart.getCartItems().add(item);
            cartItemRepository.save(item);
        }

        cart.setTotalAmount(cart.getTotalAmount().add(product.price().multiply(BigDecimal.valueOf(request.quantity()))));
        return buildCartResponse(cart);
    }

    @Transactional
    public CartResponse updateItemQuantity(Long cartItemId, CartItemQuantityRequest request, Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(CartEmptyException::new);

        CartItem cartItem = cartItemRepository.findByIdAndCart(cartItemId, cart).orElseThrow(() -> new CartItemNotFoundException(cartItemId));
        int newQuantity = request.quantity();
        int oldQuantity = cartItem.getQuantity();

        if (oldQuantity == newQuantity) {
            return buildCartResponse(cart);
        }
        if (request.quantity() == 0) {
            BigDecimal amount = cartItem.getPrice().multiply(BigDecimal.valueOf(oldQuantity));
            cart.getCartItems().remove(cartItem);
            cart.setTotalAmount(cart.getTotalAmount().subtract(amount));
        } else {
            cartItem.setQuantity(newQuantity);
            BigDecimal delta = cartItem.getPrice().multiply(BigDecimal.valueOf(newQuantity - oldQuantity));
            cart.setTotalAmount(cart.getTotalAmount().add(delta));
        }

        return buildCartResponse(cart);
    }

    @Transactional
    public CartResponse removeItem(Long cartItemId, Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(CartEmptyException::new);

        CartItem cartItem = cartItemRepository.findByIdAndCart(cartItemId, cart).orElseThrow(() -> new CartItemNotFoundException(cartItemId));
        cart.getCartItems().remove(cartItem);
        cart.setTotalAmount(cart.getTotalAmount().subtract(cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()))));

        return buildCartResponse(cart);
    }

    @Transactional
    public void clearCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(CartEmptyException::new);

        cart.setTotalAmount(BigDecimal.ZERO);
        cart.getCartItems().clear();
    }

    @Transactional
    public void clearCartById(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(CartEmptyException::new);

        cart.setTotalAmount(BigDecimal.ZERO);
        cart.getCartItems().clear();
    }

    private CartResponse buildCartResponse(Cart cart) {
        List<CartItemResponse> items = cart.getCartItems()
                .stream()
                .map(item -> {
                    ProductResponse product = productClientService.getProductById(item.getProductId());
                    return cartMapper.toCartItemResponse(item, product);
                })
                .toList();

        return new CartResponse(cart.getId(), cart.getTotalAmount(), items);
    }
}
