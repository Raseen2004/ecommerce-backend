package dev.raseen2004.e_commerce_learning.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.raseen2004.e_commerce_learning.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{
    Optional<Cart> findByCustomerId(Long customerId);
}
