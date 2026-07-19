package dev.raseen2004.e_commerce_learning.controller;

import dev.raseen2004.e_commerce_learning.dto.request.CartItemRequest;
import dev.raseen2004.e_commerce_learning.dto.response.CartResponse;
import dev.raseen2004.e_commerce_learning.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers/{customerId}/cart")
@RequiredArgsConstructor
public class CartController {
  private final CartService cartService;

  @GetMapping
  public ResponseEntity<CartResponse> getCartByCustomerId(
      @PathVariable Long customerId) {
    return ResponseEntity.ok(cartService.getCartByCustomerId(customerId));
  }

  @PostMapping("/items")
  public ResponseEntity<CartResponse> addItemToCart(
      @PathVariable Long customerId,
      @Valid @RequestBody CartItemRequest request) {
    return ResponseEntity.ok(cartService.addItemToCart(customerId, request));
  }

  @PutMapping("/items/{cartItemId}/{quantity}")
  public ResponseEntity<CartResponse> updateCartItem(
      @PathVariable Long customerId, @PathVariable Long cartItemId,
      @PathVariable Integer quantity) {
    return ResponseEntity.ok(
        cartService.updateCartItem(customerId, cartItemId, quantity));
  }

  @DeleteMapping("/items/{cartItemId}")
  public ResponseEntity<CartResponse> removeCartItem(
      @PathVariable Long customerId, @PathVariable Long cartItemId) {
    return ResponseEntity.ok(
        cartService.removeCartItem(customerId, cartItemId));
  }

  @DeleteMapping
  public ResponseEntity<Void> clearCart(@PathVariable Long customerId) {
    cartService.clearCart(customerId);

    return ResponseEntity.noContent().build();
  }
}
