package com.dwivedicomms.springinterviewlab.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank
    String name;

    String description;

    BigDecimal price;

    Integer stockQuantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

}
