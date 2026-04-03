package com.ecommerce.orderservice.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long productId) {
        super("Produit introuvable avec l'id : " + productId);
    }
}
