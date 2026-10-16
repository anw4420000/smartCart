package com.smartcart.smartcart_api.cart;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public List<CartItemResponse> getCart(
        @AuthenticationPrincipal UserDetails principal
    ) {
        return cartService.getCart(getUserId(principal));
    }

    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    public CartItemResponse addItem(
        @AuthenticationPrincipal UserDetails principal,
        @Valid @RequestBody AddCartItemRequest request
    ) {
        return cartService.addItem(getUserId(principal), request);
    }

    @PutMapping("/items/{id}")
    public CartItemResponse updateQuantity(
        @AuthenticationPrincipal UserDetails principal,
        @PathVariable UUID id,
        @Valid @RequestBody UpdateCartItemRequest request
    ) {
        return cartService.updateQuantity(
            getUserId(principal), id, request
        );
    }

    @DeleteMapping("/items/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeItem(
        @AuthenticationPrincipal UserDetails principal,
        @PathVariable UUID id
    ) {
        cartService.removeItem(getUserId(principal), id);
    }

    private UUID getUserId(UserDetails principal) {
        if (principal == null) {
            throw new IllegalStateException(
                "An authenticated user is required"
            );
        }

        // Adapt this lookup to your User entity and authentication setup.
        // UserDetails.getUsername() is usually an email, not a UUID.
        throw new UnsupportedOperationException(
            "Connect this method to your authenticated User entity"
        );
    }
}