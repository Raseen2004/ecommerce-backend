package dev.raseen2004.e_commerce_learning.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.raseen2004.e_commerce_learning.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrderId(Long orderId);
}
