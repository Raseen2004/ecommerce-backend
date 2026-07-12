package dev.raseen2004.e_commerce_learning.dto.request;

import lombok.Data;

@Data
public class CartItemRequest {

    private Long productId;

    private Integer quantity;
}