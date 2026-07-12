package dev.raseen2004.e_commerce_learning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.raseen2004.e_commerce_learning.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
    List<Order> findByCustomerId(Long customerId);
}
