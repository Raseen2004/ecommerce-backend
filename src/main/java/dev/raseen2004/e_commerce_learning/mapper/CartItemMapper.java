package dev.raseen2004.e_commerce_learning.mapper;

import java.math.BigDecimal;

import dev.raseen2004.e_commerce_learning.dto.request.CartItemRequest;
import dev.raseen2004.e_commerce_learning.dto.response.CartItemResponse;
import dev.raseen2004.e_commerce_learning.entity.CartItem;

public class CartItemMapper {

    public static CartItem toEntity(CartItemRequest request) {

        return CartItem.builder()
                .quantity(request.getQuantity())
                .build();
    }

    public static CartItemResponse toResponse(CartItem item) {

        BigDecimal subtotal = item.getProduct().getPrice()
                .multiply(BigDecimal.valueOf(item.getQuantity()));

        return CartItemResponse.builder()
                .id(item.getId())
                .productName(item.getProduct().getName())
                .price(item.getProduct().getPrice())
                .quantity(item.getQuantity())
                .subtotal(subtotal)
                .build();
    }
}