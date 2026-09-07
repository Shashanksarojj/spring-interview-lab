package com.dwivedicomms.springinterviewlab.java8lab;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CollectorsDemo {
    public static void main(String[] args) {
        List<Item> items = List.of(
                new Item("Pixel 9", "Phones", new BigDecimal("799.00")),
                new Item("Galaxy S25", "Phones", new BigDecimal("899.00")),
                new Item("Effective Java", "Books", new BigDecimal("45.50"))
        );

        Map<String, List<String>> namesByCategory = items.stream()
                .collect(Collectors.groupingBy(Item::category, Collectors.mapping(Item::name, Collectors.toList())));
        System.out.println("Names by category: " + namesByCategory);

        Map<String, BigDecimal> totalByCategory = items.stream()
                .collect(Collectors.groupingBy(Item::category, Collectors.reducing(BigDecimal.ZERO, Item::price, BigDecimal::add)));
        System.out.println("Total price by category: " + totalByCategory);
    }
}
