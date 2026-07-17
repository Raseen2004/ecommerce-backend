package dev.raseen2004.e_commerce_learning.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.raseen2004.e_commerce_learning.dto.response.PaymentResponse;
import dev.raseen2004.e_commerce_learning.entity.Payment;
import dev.raseen2004.e_commerce_learning.exception.ResourceNotFoundException;
import dev.raseen2004.e_commerce_learning.mapper.PaymentMapper;
import dev.raseen2004.e_commerce_learning.repository.PaymentRepository;
import dev.raseen2004.e_commerce_learning.service.PaymentService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {
    
    private final PaymentRepository paymentRepository;

    @Override
    public PaymentResponse getPaymentId(Long id) {
        Payment payment = paymentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: "+ id));

        return PaymentMapper.toResponse(payment);
    }

    @Override
    public PaymentResponse getPaymentByOderId(Long orderId) {
        Payment payment = paymentRepository
            .findByOrderId(orderId)
            .orElseThrow(() -> 
                new ResourceNotFoundException("Payment not found for order id: "+orderId)
            );

        return PaymentMapper.toResponse(payment);
    }
    
}
