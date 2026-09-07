package com.dwivedicomms.springinterviewlab.service;

import com.dwivedicomms.springinterviewlab.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public BigDecimal calculatedOrderTotal(Long orderId) {
        return orderRepository.findById(orderId)
                .map(order -> order.getOrderItemsList().stream()
                        .map(item -> item.getPriceAtPurchase().multiply(BigDecimal.valueOf(item.getQuantity())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .orElseThrow(() -> new NoSuchElementException("Order not Found: " + orderId));

    }
}
