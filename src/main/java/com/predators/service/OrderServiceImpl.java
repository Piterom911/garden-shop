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

    // Получение всех заказов (например, для админки)
    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    // Получение конкретного заказа по ID
    @Override
    public Order getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
    }

    // Создание нового заказа — устанавливаем дату создания и обновления
    @Override
    public Order create(Order order) {
        Timestamp now = Timestamp.from(Instant.now());
        order.setCreatedAt(now);
        order.setUpdatedAt(now);
        return orderRepository.save(order);
    }

    // Удаление заказа по ID
    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    // Обновление статуса заказа вручную (например, админом)
    @Override
    public Order updateStatus(Long id, String newStatus) {
        Order order = getById(id);
        order.setStatus(OrderStatus.valueOf(newStatus));
        order.setUpdatedAt(Timestamp.from(Instant.now()));
        return orderRepository.save(order);
    }

    // Получение всех заказов конкретного пользователя
    public List<Order> getAllByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }

    // Отмена заказа (если статус позволяет)
    public Order cancelOrder(Long id) {
        Order order = getById(id);
        OrderStatus currentStatus = order.getStatus();

        if (currentStatus == OrderStatus.CREATED || currentStatus == OrderStatus.PENDING_PAYMENT) {
            order.setStatus(OrderStatus.CANCELLED);
            order.setUpdatedAt(Timestamp.from(Instant.now()));
            return orderRepository.save(order);
        } else {
            throw new IllegalStateException("Order cannot be cancelled in current status: " + currentStatus);
        }
    }
}
