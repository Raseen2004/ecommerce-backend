package dev.raseen2004.e_commerce_learning.security;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.raseen2004.e_commerce_learning.entity.Customer;
import dev.raseen2004.e_commerce_learning.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
        private final CustomerRepository customerRepository;

        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
            Customer customer = customerRepository.findByEmail(email)
                    .orElseThrow(() ->
                        new UsernameNotFoundException("Customer not found with email: " +email)
                    );

            return User.builder()
                    .username(customer.getEmail())
                    .password(customer.getPassword())
                    .roles(customer.getRole().name())
                    .build();
        }
}
