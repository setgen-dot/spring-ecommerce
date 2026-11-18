package com.example.repository;

import com.example.entity.Cart;
import com.example.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartAndProductId(Cart cart, Long productId);

    Optional<CartItem> findByIdAndCart(Long cartItemId, Cart cart);
}
