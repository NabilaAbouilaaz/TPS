package com.ecommerce.orderservice.exception;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(Long productId, Integer requested) {
        super("Stock insuffisant pour le produit " + productId
                + ". Quantité demandée : " + requested);
    }
}
