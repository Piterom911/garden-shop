package com.predators.service;

import com.predators.entity.Order;
import com.predators.entity.Product;
import com.predators.entity.enums.OrderStatus;
import com.predators.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final OrderService orderService;

    @Override
    public List<Product> topPaidItems() {
        List<Order> paidOrders = orderService.getAllByStatus(OrderStatus.COMPLETED);
        if (paidOrders.isEmpty()) {
            throw  new ProductNotFoundException("List of paid orders is empty");
        }

        Map<Product, Integer> productCounts = new HashMap<>();
        paidOrders.forEach(order -> {
            order.getOrderItems().forEach(item -> {
                Product product = item.getProduct();
                productCounts.put(product, productCounts.getOrDefault(product, 0) + 1);
            });
        });

        List<Map.Entry<Product, Integer>> sortedList = new ArrayList<>(productCounts.entrySet());
        sortedList.sort(Map.Entry.<Product, Integer>comparingByValue().reversed());

        List<Product> topProducts = new ArrayList<>();
        int limit = Math.min(10, sortedList.size());
        for (int i = 0; i < limit; i++) {
            topProducts.add(sortedList.get(i).getKey());
        }
        return topProducts;
    }
}
