package com.dwivedicomms.springinterviewlab.service;

import com.dwivedicomms.springinterviewlab.domain.Order;
import com.dwivedicomms.springinterviewlab.domain.OrderItems;
import com.dwivedicomms.springinterviewlab.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderService orderService;

    @Test
    void calculatesTotalFromOrderItems() {
        Order order = new Order();
        OrderItems item1 = new OrderItems();
        item1.setPriceAtPurchase(new BigDecimal("10.00"));
        item1.setQuantity(2);
        OrderItems item2 = new OrderItems();
        item2.setPriceAtPurchase(new BigDecimal("5.00"));
        item2.setQuantity(1);
        order.setOrderItemsList(List.of(item1, item2));

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        BigDecimal total = orderService.calculatedOrderTotal(1L);

        assertThat(total).isEqualByComparingTo("25.00");
    }

    @Test
    void throwsWhenOrderMissing() {
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> orderService.calculatedOrderTotal(99L));
    }
}