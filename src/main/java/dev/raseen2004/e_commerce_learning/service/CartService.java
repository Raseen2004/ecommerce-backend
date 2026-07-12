package dev.raseen2004.e_commerce_learning.service;

import dev.raseen2004.e_commerce_learning.dto.request.CartItemRequest;
import dev.raseen2004.e_commerce_learning.dto.response.CartResponse;

public interface CartService {
    CartResponse getCartByCustomerId(Long customerId);

    CartResponse addItemToCart(
            Long customerId,
            CartItemRequest request);

    CartResponse updateCartItem(
            Long customerId,
            Long cartItemId,
            Integer quantity);

    CartResponse removeCartItem(
            Long customerId,
            Long cartItemId);

    void clearCart(Long customerId);
}
