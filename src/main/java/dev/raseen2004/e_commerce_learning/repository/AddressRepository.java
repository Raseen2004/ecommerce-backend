package dev.raseen2004.e_commerce_learning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.raseen2004.e_commerce_learning.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{
    List<Address> findByCustomerId(Long customerId);
}
