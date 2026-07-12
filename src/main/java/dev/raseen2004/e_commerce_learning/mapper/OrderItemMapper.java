package dev.raseen2004.e_commerce_learning.mapper;

import dev.raseen2004.e_commerce_learning.dto.response.OrderItemResponse;
import dev.raseen2004.e_commerce_learning.entity.OrderItem;

public class OrderItemMapper {

    public static OrderItemResponse toResponse(OrderItem item) {

        return OrderItemResponse.builder()
                .productName(item.getProduct().getName())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .subtotal(item.getSubtotal())
                .build();
    }
}