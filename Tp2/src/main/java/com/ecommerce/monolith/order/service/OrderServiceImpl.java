package com.ecommerce.monolith.order.service;

import com.ecommerce.monolith.customer.service.CustomerPublicService;
import com.ecommerce.monolith.order.dto.CreateOrderRequest;
import com.ecommerce.monolith.order.dto.OrderDTO;
import com.ecommerce.monolith.order.mapper.OrderMapper;
import com.ecommerce.monolith.order.model.Order;
import com.ecommerce.monolith.order.repository.OrderRepository;
import com.ecommerce.monolith.product.dto.ProductDTO;
import com.ecommerce.monolith.product.service.ProductPublicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final ProductPublicService productPublicService;
    private final CustomerPublicService customerPublicService;

    @Override
    public List<OrderDTO> getAllOrders() {
        return mapper.toDTOList(repository.findAll());
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        return mapper.toDTO(order);
    }

    @Override
    public OrderDTO createOrder(CreateOrderRequest request) {
        if (!customerPublicService.existsById(request.getCustomerId())) {
            throw new RuntimeException("Customer not found with id: " + request.getCustomerId());
        }

        ProductDTO product = productPublicService.getProductById(request.getProductId());

        BigDecimal totalAmount = product.getPrice()
                .multiply(BigDecimal.valueOf(request.getQuantity()));

        Order order = mapper.toEntity(request);
        order.setTotalAmount(totalAmount);
        order.setOrderDate(LocalDateTime.now());

        Order savedOrder = repository.save(order);
        return mapper.toDTO(savedOrder);
    }

    @Override
    public List<OrderDTO> getOrderHistoryByCustomerId(Long customerId) {
        if (!customerPublicService.existsById(customerId)) {
            throw new RuntimeException("Customer not found with id: " + customerId);
        }

        return mapper.toDTOList(repository.findByCustomerId(customerId));
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        repository.delete(order);
    }
}