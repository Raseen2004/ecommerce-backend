package dev.raseen2004.e_commerce_learning.service;

import dev.raseen2004.e_commerce_learning.dto.response.PaymentResponse;

public interface PaymentService {
    PaymentResponse getPaymentId(Long id);

    PaymentResponse getPaymentByOderId(Long orderId);
}
