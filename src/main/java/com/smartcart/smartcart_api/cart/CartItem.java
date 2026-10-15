package com.smartcart.smartcart_api.cart;

import java.time.Instant;
import java.util.UUID;

import com.smartcart.smartcart_api.product.Product;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class CartItem {
    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "user_id" , nullable = false)
    private UUID userId;

    @ManyToOne (fetch = FetchType.LAZY , optional = false)
    @JoinColumn (name = "product_id" , nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column (name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected CartItem() {

    }
    
    public CartItem(UUID userId, Product product, Integer quantity){
        this.userId = userId;
        this.product = product;
        this.quantity = quantity;
    }

    @PrePersist 
    protected void onCreate() {
        Instant now = Instant.now();
        createdAt =now;
        updatedAt = now;
    }

    @PreUpdate 
    protected void onUpdate() {
        updatedAt = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    
}
