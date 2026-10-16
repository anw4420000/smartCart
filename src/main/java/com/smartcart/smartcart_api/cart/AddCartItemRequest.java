package com.smartcart.smartcart_api.cart;

import jakarta.validation.constraints.NotNull;

public record AddCartItemRequest(
    @NotNull UUID productId,
    @NotNull @Min(1) Integer quantity
) {
    
}
