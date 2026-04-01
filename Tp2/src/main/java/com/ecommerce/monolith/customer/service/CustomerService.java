package com.ecommerce.monolith.customer.service;

import com.ecommerce.monolith.customer.dto.CreateCustomerRequest;
import com.ecommerce.monolith.customer.dto.CustomerDTO;

import java.util.List;

public interface CustomerService extends CustomerPublicService {
    List<CustomerDTO> getAllCustomers();
    CustomerDTO createCustomer(CreateCustomerRequest request);
    CustomerDTO updateCustomer(Long id, CreateCustomerRequest request);
    void deleteCustomer(Long id);
}
