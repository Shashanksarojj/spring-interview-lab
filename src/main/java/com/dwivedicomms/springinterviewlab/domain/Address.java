package com.dwivedicomms.springinterviewlab.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String street;

    String city;

    String pinCode;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;
}
