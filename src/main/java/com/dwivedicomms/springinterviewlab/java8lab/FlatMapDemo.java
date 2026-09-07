package com.dwivedicomms.springinterviewlab.java8lab;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FlatMapDemo {
    public static void main(String[] args) {
        List<LabOrder> orders = List.of(
                new LabOrder("Alice", List.of(
                        new Item("Pixel 9", "Phones", new BigDecimal("799.00")),
                        new Item("Effective Java", "Books", new BigDecimal("45.50")))),
                new LabOrder("Bob", List.of(
                        new Item("Galaxy S25", "Phones", new BigDecimal("899.00"))))
        );

        List<Item> allItems = orders.stream()
                .flatMap(order -> order.items().stream())
                .toList();
        System.out.println("All items across all orders: " + allItems.size());

        Set<String> distinctCategories = orders.stream()
                .flatMap(order -> order.items().stream())
                .map(Item::category)
                .collect(Collectors.toSet());
        System.out.println("Distinct categories purchased: " + distinctCategories);
    }
}