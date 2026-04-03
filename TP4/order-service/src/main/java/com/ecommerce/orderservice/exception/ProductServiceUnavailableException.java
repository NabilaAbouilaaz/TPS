package com.ecommerce.orderservice.exception;

public class ProductServiceUnavailableException extends RuntimeException {
    public ProductServiceUnavailableException() {
        super("Le service Product est indisponible. Veuillez réessayer plus tard.");
    }
}
