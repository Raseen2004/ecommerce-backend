package dev.raseen2004.e_commerce_learning.dto.request;

import lombok.Data;

@Data
public class OrderRequest {

    private Long addressId;

    private String paymentMethod;
}