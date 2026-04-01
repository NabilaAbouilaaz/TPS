package com.ecommerce.monolith.customer.service;

import com.ecommerce.monolith.customer.dto.CustomerDTO;

public interface CustomerPublicService {
    boolean existsById(Long id);
    CustomerDTO getCustomerById(Long id);
}
