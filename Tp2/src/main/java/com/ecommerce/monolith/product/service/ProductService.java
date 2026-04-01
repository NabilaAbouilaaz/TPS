package com.ecommerce.monolith.product.service;

import com.ecommerce.monolith.product.dto.CreateProductRequest;
import com.ecommerce.monolith.product.dto.ProductDTO;

import java.util.List;

public interface ProductService extends ProductPublicService {
    List<ProductDTO> getAllProducts();
    ProductDTO createProduct(CreateProductRequest request);
    ProductDTO updateProduct(Long id, CreateProductRequest request);
    void deleteProduct(Long id);
}
