package com.ecommerce.monolith.product.service;

import com.ecommerce.monolith.product.dto.ProductDTO;

public interface ProductPublicService {
    boolean existsById(Long id);
    ProductDTO getProductById(Long id);
}