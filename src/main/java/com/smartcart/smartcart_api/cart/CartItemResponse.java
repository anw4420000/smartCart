
package com.smartcart.smartcart_api.cart;

import java.math.BigDecimal;
import java.util.UUID;

public record CartItemResponse(
UUID id,
UUID productId,
String productName,
BigDecimal unitPrice,
Integer quantity,
BigDecimal subtotal
) {
public static CartItemResponse from(CartItem item) {
BigDecimal price = item.getProduct().getPrice();

    return new CartItemResponse(
        item.getId(),
        item.getProduct().getId(),
        item.getProduct().getName(),
        price,
        item.getQuantity(),
        price.multiply(BigDecimal.valueOf(item.getQuantity()))
    );
}

}