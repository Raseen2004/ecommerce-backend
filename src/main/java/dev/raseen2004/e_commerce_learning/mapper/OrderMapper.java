package dev.raseen2004.e_commerce_learning.mapper;

import java.util.stream.Collectors;

import dev.raseen2004.e_commerce_learning.dto.response.OrderResponse;
import dev.raseen2004.e_commerce_learning.entity.Order;

public class OrderMapper {

    public static OrderResponse toResponse(Order order) {

        return OrderResponse.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .items(order.getOrderItems()
                        .stream()
                        .map(OrderItemMapper::toResponse)
                        .collect(Collectors.toList()))
                .build();
    }
}