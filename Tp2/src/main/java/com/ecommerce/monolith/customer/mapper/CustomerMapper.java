package com.ecommerce.monolith.customer.mapper;

import com.ecommerce.monolith.customer.dto.CreateCustomerRequest;
import com.ecommerce.monolith.customer.dto.CustomerDTO;
import com.ecommerce.monolith.customer.model.Customer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerMapper {

    public CustomerDTO toDTO(Customer customer) {
        if (customer == null) return null;

        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setFullName(customer.getFullName());
        dto.setEmail(customer.getEmail());
        return dto;
    }

    public List<CustomerDTO> toDTOList(List<Customer> customers) {
        return customers.stream().map(this::toDTO).toList();
    }

    public Customer toEntity(CreateCustomerRequest request) {
        if (request == null) return null;

        Customer customer = new Customer();
        customer.setFullName(request.getFullName());
        customer.setEmail(request.getEmail());
        return customer;
    }

    public void updateEntity(CreateCustomerRequest request, Customer customer) {
        customer.setFullName(request.getFullName());
        customer.setEmail(request.getEmail());
    }
}