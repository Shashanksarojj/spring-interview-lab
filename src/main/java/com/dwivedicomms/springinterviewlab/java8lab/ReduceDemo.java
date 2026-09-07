package com.dwivedicomms.springinterviewlab.java8lab;

import java.math.BigDecimal;
import java.util.List;

public class ReduceDemo {
    public static void main(String[] args) {
        List<Item> items = List.of(
                new Item("Pixel 9", "Phones", new BigDecimal("799.00")),
                new Item("Galaxy S25", "Phones", new BigDecimal("899.00")),
                new Item("Effective Java", "Books", new BigDecimal("45.50"))
        );

        // 3-arg reduce: result type (BigDecimal) differs from element type (Item), so a combiner is required
        BigDecimal total = items.stream()
                .reduce(BigDecimal.ZERO,
                        (partialTotal, item) -> partialTotal.add(item.price()),
                        BigDecimal::add);
        System.out.println("Total (sequential): " + total);

        BigDecimal parallelTotal = items.parallelStream()
                .reduce(BigDecimal.ZERO,
                        (partialTotal, item) -> partialTotal.add(item.price()),
                        (a, b) -> {
                            System.out.println("combiner invoked: " + a + " + " + b);
                            return a.add(b);
                        });
        System.out.println("Total (parallel): " + parallelTotal);
    }
}
