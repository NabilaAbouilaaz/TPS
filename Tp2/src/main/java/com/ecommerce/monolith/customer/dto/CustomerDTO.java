package com.ecommerce.monolith.customer.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;
    private String fullName;
    private String email;
}
