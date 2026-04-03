package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.client.ProductClient;
import com.ecommerce.orderservice.dto.ProductDTO;
import com.ecommerce.orderservice.exception.InsufficientStockException;
import com.ecommerce.orderservice.exception.ProductNotFoundException;
import com.ecommerce.orderservice.exception.ProductServiceUnavailableException;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.repository.OrderRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order createOrder(Long productId, Integer quantity) {
        ProductDTO product;

        // Exercice 3 : Gérer le cas où le service Product est indisponible
        try {
            product = productClient.getProductById(productId);
        } catch (FeignException.NotFound e) {
            // Exercice 3 : Gérer le cas où le produit n'existe pas
            throw new ProductNotFoundException(productId);
        } catch (FeignException e) {
            throw new ProductServiceUnavailableException();
        }

        // Exercice 1 : Vérifier le stock disponible avant de créer la commande
        if (product.getStock() < quantity) {
            throw new InsufficientStockException(productId, quantity);
        }
        // Créer la commande
        Order order = new Order();
        order.setProductId(productId);
        order.setProductName(product.getName());
        order.setQuantity(quantity);
        order.setTotalPrice(product.getPrice() * quantity);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        Order savedOrder = orderRepository.save(order);
        // Exercice 1 : Mettre à jour le stock dans Product Service
        try {
            productClient.updateStock(productId, quantity);
        } catch (FeignException e) {
            // Si la mise à jour du stock échoue, on annule la commande
            orderRepository.delete(savedOrder);
            throw new ProductServiceUnavailableException();
        }
        return savedOrder;
    }}

