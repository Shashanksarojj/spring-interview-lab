package com.dwivedicomms.springinterviewlab.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    Order order;

    @ManyToOne
    Product product;

    Integer quantity;

    BigDecimal priceAtPurchase;
}
