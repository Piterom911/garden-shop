package com.predators.util;

import com.predators.entity.Order;
import com.predators.repository.OrderRepository;
import com.predators.entity.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderScheduler {

    private final OrderRepository orderRepository;
    private final Executor pool;

    @Async(pool)
    @Scheduled(fixedRate = 30000)
    public void changeStatusToPending(Order order) {
        order.setStatus(OrderStatus.PENDING);
        orderRepository.save(order);
        changeStatusToPaid(order);
    }

    @Scheduled(fixedRate = 30000)
    public void changeStatusToPaid(Order order) {
        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);
    }
}
