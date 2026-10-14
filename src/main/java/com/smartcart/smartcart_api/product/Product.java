package com.smartcart.smartcart_api.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;;

@Entity 
@Table(
    name = "products",
    uniqueConstraints = {
        @UniqueConstraint (name = "uk_product_sku" , columnNames = "sku")
    }
)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false , length =150)
    private String name;

    @Column(nullable = false , length =2000)
    private String description;

    @Column(nullable = false , precision = 12 , scale=2)
    private BigDecimal price;

    @Column(nullable = false , length =50)
    private String sku;

    @Column(nullable = false )
    private Integer stockQuantity;

    @Column(nullable = false )
    private boolean active;

    @Column(nullable = false , updatable = false)
    private Instant createdAt;

    protected  Product() {
        //required by JPA
    }

    public Product(
        String name,
        String description,
        BigDecimal price,
        String sku,
        Integer stockQuantity) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.sku = sku ;
    this.stockQuantity = stockQuantity;
    }

    @PrePersist 
    void onCreate() {
        createdAt = Instant.now();
    }

    public UUID getId() {return id;}
    public String getName() {return name;}
    public String getDescription() {return description;}
    public BigDecimal getPrice() {return price;}
    public String getSku() {return sku;}
    public Integer getStockQuantity() {return  stockQuantity;}
    public boolean isActive() { return active; }
    public Instant getCreatedAt() { return createdAt; }
}
