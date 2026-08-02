package dev.raseen2004.e_commerce_learning.service.impl;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.raseen2004.e_commerce_learning.dto.auth.AuthResponse;
import dev.raseen2004.e_commerce_learning.dto.auth.LoginRequest;
import dev.raseen2004.e_commerce_learning.dto.auth.RegisterRequest;
import dev.raseen2004.e_commerce_learning.entity.Cart;
import dev.raseen2004.e_commerce_learning.entity.Customer;
import dev.raseen2004.e_commerce_learning.entity.Role;
import dev.raseen2004.e_commerce_learning.exception.ResourceNotFoundException;
import dev.raseen2004.e_commerce_learning.repository.CustomerRepository;
import dev.raseen2004.e_commerce_learning.security.JwtService;
import dev.raseen2004.e_commerce_learning.service.AuthService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        Customer customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .build();

        Cart cart = Cart.builder()
                .createdAt(LocalDateTime.now())
                .customer(customer)
                .build();

        customer.setCart(cart);

        Customer savedCustomer = customerRepository.save(customer);

        String token = jwtService.generateToken(createUser(savedCustomer));

        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .customerId(savedCustomer.getId())
                .email(savedCustomer.getEmail())
                .role(savedCustomer.getRole().name())
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        Customer customer = customerRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found with email: " + request.getEmail()));

        String token = jwtService.generateToken(createUser(customer));

        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .customerId(customer.getId())
                .email(customer.getEmail())
                .role(customer.getRole().name())
                .build();
    }

    private User createUser(Customer customer) {

        return new User(
                customer.getEmail(),
                customer.getPassword(),
                java.util.List.of(
                        new SimpleGrantedAuthority(
                                "ROLE_" + customer.getRole().name())));
    }
}
