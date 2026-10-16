package com.smartcart.smartcart_api.cart;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.smartcart.smartcart_api.product.Product;
import com.smartcart.smartcart_api.product.ProductRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Service 
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartService(
        CartItemRepository cartItemRepository,
        ProductRepository productRepository
    ) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }
    @Transactional 
    public List<CartItemResponse> getCart(UUID userId){
        return cartItemRepository.findByUserId(userId)
            .stream()
            .map(CartItemResponse::from)
            .toList();
            }
    @Transactional         
    public CartItemResponse addItem(
        UUID userId,
        AddCartItemRequest request
    ){
        Product product = productRepository.findById(request.productId())
        .orElseThrow(() ->
            new EntityNotFoundException("Product not found")
        );

    validateStock(product, request.quantity());

    CartItem item = cartItemRepository
        .findByUserIdAndProductId(userId, product.getId())
        .orElse(null);

    if (item == null) {
        item = new CartItem(
            userId,
            product,
            request.quantity()
        );
    } else {
        int newQuantity = item.getQuantity() + request.quantity();
        validateStock(product, newQuantity);
        item.setQuantity(newQuantity);
    }
        return CartItemResponse.from(cartItemRepository.save(item));

    }
    private void validateStock(Product product, int quantity) {
    if (quantity < 1) {
        throw new IllegalArgumentException(
            "Quantity must be at least 1"
        );
    }

    if (product.getStockQuantity() == null ||
        product.getStockQuantity() < quantity) {
        throw new IllegalArgumentException(
            "Insufficient stock for product: "
                + product.getName()
        );
    }
}        
}
