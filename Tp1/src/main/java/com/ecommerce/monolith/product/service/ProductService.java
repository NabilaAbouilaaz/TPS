package com.ecommerce.monolith.product.service;

import com.ecommerce.monolith.product.model.Product;
import com.ecommerce.monolith.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository repository;

    @Transactional(readOnly = true)
    public List<Product> getAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Product getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable avec l'id : " + id));
    }

    public Product create(Product product) {
        return repository.save(product);
    }

    public Product update(Long id, Product newProduct) {
        Product product = getById(id);
        product.setName(newProduct.getName());
        product.setDescription(newProduct.getDescription());
        product.setPrice(newProduct.getPrice());
        product.setStock(newProduct.getStock());
        product.setCategory(newProduct.getCategory());
        return repository.save(product);
    }

    public void delete(Long id) {
        Product product = getById(id);
        repository.delete(product);
    }
}
