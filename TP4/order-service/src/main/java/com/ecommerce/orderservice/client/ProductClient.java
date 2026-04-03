package com.ecommerce.orderservice.client;

import com.ecommerce.orderservice.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/api/products/{id}")
    ProductDTO getProductById(@PathVariable Long id);

    // Exercice 1 : Appel du endpoint PUT /api/products/{id}/stock
    @PutMapping("/api/products/{id}/stock")
    void updateStock(@PathVariable Long id, @RequestParam Integer quantity);
}