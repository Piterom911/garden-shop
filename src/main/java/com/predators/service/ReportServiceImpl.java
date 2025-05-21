package com.predators.service;

import com.predators.entity.Order;
import com.predators.entity.OrderItem;
import com.predators.entity.Product;
import com.predators.entity.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final OrderService orderService;

    @Value("${orders.top.limit:10}")
    private int topLimit;

    @Override
    public List<Product> topItems(OrderStatus status) {
        List<Product> productList = orderService.getTopProducts(status);

        List<Product> topProducts = new ArrayList<>();
        int limit = Math.min(topLimit, productList.size());
        for (int i = 0; i < limit; i++) {
            topProducts.add(productList.get(i));
        }
        return topProducts;
    }

    @Override
    public Set<Product> waitingPaymentMoreNDays(Long days) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dateTime = now.minusDays(days);
        Timestamp data = Timestamp.valueOf(dateTime);

        List<Order> orders = orderService.findAllByStatusAndUpdatedAtBefore(OrderStatus.PENDING, data );

        return orders;
    }

    @Override
    public BigDecimal getProfit(Long days, Long month, Long year) {
        LocalDateTime date = LocalDateTime.now();
        if (days != null) {
            date = date.minusDays(days);
        }
        if (month != null) {
            date = date.minusMonths(month);
        }
        if (year != null) {
            date = date.minusYears(year);
        }
        List<Order> allByStatusAndDate = orderService.getAllByStatusAndAfterDate(OrderStatus.COMPLETED, Timestamp.valueOf(date));
        return allByStatusAndDate.stream()
                .map(Order::getOrderItems).flatMap(List::stream)
                .map(OrderItem::getPriceAtPurchase)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
