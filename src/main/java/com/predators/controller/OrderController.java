package com.predators.controller;

import com.predators.dto.order.OrderRequestDto;
import com.predators.dto.order.OrderResponseDto;
import com.predators.dto.converter.OrderConverter;
import com.predators.entity.Order;
import com.predators.entity.enums.OrderStatus;
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
    public ResponseEntity<List<OrderResponseDto>> getAll() {
        List<OrderResponseDto> list = orderService.getAll()
                .stream()
                .map(converter::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OrderStatus> create(@RequestBody OrderRequestDto dto) {
        Order created = orderService.create(converter.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(created.getStatus());
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getById(@PathVariable Long id) {
        Order order = orderService.getById(id);
        return ResponseEntity.ok(converter.toDto(order));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDto>> getByUserId(@PathVariable Long userId) {
        List<OrderResponseDto> list = orderService.getAllByUserId(userId)
                .stream()
                .map(converter::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<OrderStatus> cancel(@PathVariable Long id) {
        Order cancelled = orderService.cancelOrder(id);
        return ResponseEntity.ok(cancelled.getStatus());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderStatus> updateStatus(@PathVariable Long id,
                                                    @RequestParam String status) {
        Order updated = orderService.updateStatus(id, status);
        return ResponseEntity.ok(updated.getStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
