package com.smartcart.smartcart_api.product;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ProductResponse(
    UUID ID,
    String name,
    String description,
    BigDecimal price,
    String sku,
    Integer stockQuantity,
    boolean active,
    Instant createdAt
) {
 public static ProductResponse from (Product product) {
    return new ProductResponse(
       product.getId(),
       product.getName(),
       product.getDescription(),
       product.getPrice(),
       product.getSku(),
       product.getStockQuantity(),
       product.isActive(),
       product.getCreatedAt() );
} }
