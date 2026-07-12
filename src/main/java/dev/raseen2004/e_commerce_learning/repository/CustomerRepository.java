package dev.raseen2004.e_commerce_learning.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.raseen2004.e_commerce_learning.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findByEmail(String email);

    boolean existsByEmail(String email);
}
