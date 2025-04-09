package com.predators.service;

import com.predators.entity.Order;
import com.predators.entity.enums.OrderStatus;
import com.predators.exception.OrderNotFoundException;
import com.predators.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
    }

    @Override
    public Order create(Order order) {
        order.setCreatedAt(Timestamp.from(Instant.now()));
        return orderRepository.save(order);
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Order updateStatus(Long id, String newStatus) {
        Order order = getById(id);
        order.setStatus(OrderStatus.valueOf(newStatus));
        order.setUpdatedAt(Timestamp.from(Instant.now()));
        return orderRepository.save(order);
    }
}
