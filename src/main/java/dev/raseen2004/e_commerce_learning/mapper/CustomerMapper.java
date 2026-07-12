package dev.raseen2004.e_commerce_learning.mapper;

import dev.raseen2004.e_commerce_learning.dto.request.CustomerRequest;
import dev.raseen2004.e_commerce_learning.dto.response.CustomerResponse;
import dev.raseen2004.e_commerce_learning.entity.Customer;

public class CustomerMapper {
    public static Customer toEntity(CustomerRequest request) {
        return Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(request.getPassword())
                .build();
    }

    public static CustomerResponse toResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .createdAt(customer.getCreatedAt())
                .build();
    }
}
