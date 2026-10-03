package com.dwivedicomms.springinterviewlab.controller;

import com.dwivedicomms.springinterviewlab.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}/total")
    public BigDecimal getOrderTotal(@PathVariable Long id) {
        return orderService.calculatedOrderTotal(id);
    }
}