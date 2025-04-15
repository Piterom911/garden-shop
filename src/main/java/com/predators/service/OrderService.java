package com.predators.service;

import com.predators.entity.Order;

import java.util.List;

public interface OrderService {

    List<Order> getAll();

    Order getById(Long id);

    Order create(Order order);

    void delete(Long id);

    Order updateStatus(Long id, String newStatus);

    List<Order> getAllByUserId(Long userId);

    Order cancelOrder(Long id);
}
