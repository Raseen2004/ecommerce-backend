package dev.raseen2004.e_commerce_learning.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.raseen2004.e_commerce_learning.dto.request.CartItemRequest;
import dev.raseen2004.e_commerce_learning.dto.response.CartResponse;
import dev.raseen2004.e_commerce_learning.entity.Cart;
import dev.raseen2004.e_commerce_learning.entity.CartItem;
import dev.raseen2004.e_commerce_learning.entity.Product;
import dev.raseen2004.e_commerce_learning.exception.ResourceNotFoundException;
import dev.raseen2004.e_commerce_learning.mapper.CartMapper;
import dev.raseen2004.e_commerce_learning.repository.CartItemRepository;
import dev.raseen2004.e_commerce_learning.repository.CartRepository;
import dev.raseen2004.e_commerce_learning.repository.ProductRepository;
import dev.raseen2004.e_commerce_learning.service.CartService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public CartResponse getCartByCustomerId(Long customerId) {

        return CartMapper.toResponse(
                findCartByCustomerId(customerId));
    }

    @Override
    public CartResponse addItemToCart(Long customerId, CartItemRequest request) {
        Cart cart = findCartByCustomerId(customerId);

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product not found with id: " + request.getProductId()));

        validateQuantity(request.getQuantity());

        if (product.getStock() < request.getQuantity()) {
            throw new IllegalArgumentException("Insufficient product stock");
        }

        CartItem cartItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), request.getProductId())
                .orElse(null);

        if (cartItem != null) {
            int newQuantity = cartItem.getQuantity() + request.getQuantity();

            if (product.getStock() < newQuantity) {
                throw new IllegalArgumentException(
                        "Insufficient product stock");
            }

            cartItem.setQuantity(newQuantity);
        } else {
            cartItem = CartItem.builder()
                    .quantity(request.getQuantity())
                    .cart(cart)
                    .product(product)
                    .build();

            cart.getCartItems().add(cartItem);
        }

        cartRepository.save(cart);
        return CartMapper.toResponse(cart);
    }

    public CartResponse updateCartItem(Long customerId, Long cartItemId, Integer quantity) {
        Cart cart = findCartByCustomerId(customerId);

        CartItem cartItem = findCartItem(cartItemId);

        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new IllegalArgumentException(
                    "Cart item does not belong to this customer");
        }

        validateQuantity(quantity);

        if (cartItem.getProduct().getStock() < quantity) {
            throw new IllegalArgumentException(
                    "Insufficient product stock");
        }

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);

        return CartMapper.toResponse(cart);
    }

    @Override
    public CartResponse removeCartItem(Long customerId, Long cartItemId) {
        Cart cart = findCartByCustomerId(customerId);
        CartItem cartItem = findCartItem(cartItemId);

        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new IllegalArgumentException(
                    "Cart item does not belong to this customer");
        }

        cart.getCartItems().remove(cartItem);
        cartItem.setCart(null);
        cartRepository.save(cart);

        return CartMapper.toResponse(cart);
    }

    @Override
    public void clearCart(Long customerId) {
        Cart cart = findCartByCustomerId(customerId);

        cart.getCartItems().clear();

        cartRepository.save(cart);
    }

    private Cart findCartByCustomerId(Long customerId) {
        return cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for customer id: " + customerId));
    }

    private CartItem findCartItem(Long cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + cartItemId));
    }

    private void validateQuantity(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException();
        }
    }

}
