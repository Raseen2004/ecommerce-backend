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
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));

        return PaymentMapper.toResponse(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentByOrderId(Long orderId) {
        Payment payment = paymentRepository
                .findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found for order id: " + orderId));

        return PaymentMapper.toResponse(payment);
    }

    @Override
    public PaymentResponse processPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
            .orElseThrow(() -> 
                new ResourceNotFoundException("Payment not found with id: "+paymentId)
            );

        if("COMPLETED".equalsIgnoreCase(payment.getPaymentStatus())) {
            throw new IllegalArgumentException(
                "Payment is already completed"
            );
        }

        payment.setPaymentStatus("COMPLETED");

        payment.getOrder().setStatus("CONFIRMED");

        Payment savedPayment = paymentRepository.save(payment);

        return PaymentMapper.toResponse(savedPayment);
    }

}
