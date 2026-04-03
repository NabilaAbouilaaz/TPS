package com.ecommerce.productservice.service;

import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Exercice 1 : Mise à jour du stock après une commande
    public Product updateStock(Long id, Integer quantityOrdered) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable avec l'id : " + id));

        if (product.getStock() < quantityOrdered) {
            throw new RuntimeException("Stock insuffisant. Stock disponible : " + product.getStock()
                    + ", quantité demandée : " + quantityOrdered);
        }

        product.setStock(product.getStock() - quantityOrdered);
        return productRepository.save(product);
    }
}
