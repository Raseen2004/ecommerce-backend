package dev.raseen2004.e_commerce_learning.mapper;

import java.math.BigDecimal;
import java.util.stream.Collectors;

import dev.raseen2004.e_commerce_learning.dto.response.CartResponse;
import dev.raseen2004.e_commerce_learning.entity.Cart;

public class CartMapper {

    public static CartResponse toResponse(Cart cart) {

        BigDecimal total = cart.getCartItems()
                .stream()
                .map(item -> item.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        return CartResponse.builder()
                .id(cart.getId())
                .items(cart.getCartItems()
                        .stream()
                        .map(CartItemMapper::toResponse)
                        .collect(Collectors.toList()))
                .totalAmount(total)
                .build();
    }
}