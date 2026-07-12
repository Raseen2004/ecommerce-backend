package dev.raseen2004.e_commerce_learning.service;

import java.util.List;

import dev.raseen2004.e_commerce_learning.dto.request.CustomerRequest;
import dev.raseen2004.e_commerce_learning.dto.response.CustomerResponse;

/**
 * CustomerService
 */
public interface CustomerService {

    CustomerResponse createCustomer(CustomerRequest request);

    CustomerResponse getCustomerById(Long id);

    List<CustomerResponse> getAllCustomers();

    CustomerResponse updateCustomer(Long id, CustomerRequest request);

    void deleteCustomer(Long id);
}
