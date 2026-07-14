package dev.raseen2004.e_commerce_learning.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.raseen2004.e_commerce_learning.dto.request.CustomerRequest;
import dev.raseen2004.e_commerce_learning.dto.response.CustomerResponse;
import dev.raseen2004.e_commerce_learning.entity.Cart;
import dev.raseen2004.e_commerce_learning.entity.Customer;
import dev.raseen2004.e_commerce_learning.exception.ResourceNotFoundException;
import dev.raseen2004.e_commerce_learning.mapper.CustomerMapper;
import dev.raseen2004.e_commerce_learning.repository.CustomerRepository;
import dev.raseen2004.e_commerce_learning.service.CustomerService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService{
    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {
        if(customerRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        Customer customer = CustomerMapper.toEntity(request);

        customer.setCreatedAt(LocalDateTime.now());

        Cart cart = Cart.builder()
            .createdAt(LocalDateTime.now())
            .customer(customer)
            .build();

        customer.setCart(cart);

        Customer savedCustomer = customerRepository.save(customer);
        
        return CustomerMapper.toResponse(savedCustomer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse getCustomerById(Long id) {
        Customer customer = findCustomer(id);

        return CustomerMapper.toResponse(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(CustomerMapper::toResponse)
                .toList();
    }

    @Override
    public CustomerResponse updateCustomer(Long id, CustomerRequest request) {
        Customer customer = findCustomer(id);

        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setPassword(request.getPassword());

        Customer savedCustomer = customerRepository.save(customer);

        return CustomerMapper.toResponse(savedCustomer);
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = findCustomer(id);

        customerRepository.delete(customer);
    }

    private Customer findCustomer(Long id) {
        return customerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: "+ id)
        );
    }
}
