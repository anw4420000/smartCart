package com.smartcart.smartcart_api.product;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductRequest (
    @NotBlank 
    @Size(max = 150)
    String name,

    @NotBlank 
    @Size(max = 2000)
    String description,

    @NotNull 
    @DecimalMin (value = "0.01")
    @Digits (integer = 10 , fraction =2)
    BigDecimal price,

    @NotNull 
    @Size(max = 50)
    String sku,

    @NotNull 
    @Min(0)
    @Max(1000000)
    Integer stockQuantity
 ) {}

