package com.dwivedicomms.springinterviewlab.domain;

import com.dwivedicomms.springinterviewlab.enums.PaymentMethodEnum;
import com.dwivedicomms.springinterviewlab.enums.PaymentStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    Order order;

    BigDecimal amount;

    @Enumerated(EnumType.STRING)
    PaymentMethodEnum method;


    @Enumerated(EnumType.STRING)
    PaymentStatusEnum status;


    Instant paidAt;

}
