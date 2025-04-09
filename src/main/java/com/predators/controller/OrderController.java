package com.predators.controller;

import com.predators.dto.OrderRequestDto;
import com.predators.dto.OrderResponseDto;
import com.predators.dto.converter.OrderConverter;
import com.predators.entity.Order;
import com.predators.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderConverter converter;

    public OrderController(OrderService orderService, OrderConverter orderConverter) {
        this.orderService = orderService;
        this.converter = orderConverter;
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        List<OrderResponseDto> list = orderService.getAll()
                .stream()
                .map(converter::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto dto) {
        Order created = orderService.create(converter.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(converter.toDto(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id) {
        Order order = orderService.getById(id);
        return ResponseEntity.ok(converter.toDto(order));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(@PathVariable Long id,
                                                              @RequestParam String status) {
        Order updated = orderService.updateStatus(id, status);
        return ResponseEntity.ok(converter.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
