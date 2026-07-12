package dev.raseen2004.e_commerce_learning.service;

import java.util.List;

import dev.raseen2004.e_commerce_learning.dto.request.OrderRequest;
import dev.raseen2004.e_commerce_learning.dto.response.OrderResponse;

public interface OrderService {
    OrderResponse placeOrder(
        Long customerId,
        OrderRequest request
    );

    OrderResponse getOrderById(Long id);

    List<OrderResponse> getOrdersByCustomerId(Long customerId);

    OrderResponse updateOrderStatus(Long id, String status);
}
