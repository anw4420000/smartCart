package com.smartcart.smartcart_api.cart;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    List<CartItem> findByUserId(UUID userId);
    Optional<CartItem> findByIdAndUserId(UUID id ,UUID userId);
    Optional<CartItem> findByUserIdAndProductId(UUID userId, UUID productId);
    void deleteByIdAndUserId(UUID id , UUID userId);
    void deleteAllByUserId(UUID userId);  
}
