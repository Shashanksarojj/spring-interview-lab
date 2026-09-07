package com.dwivedicomms.springinterviewlab.java8lab;

import java.math.BigDecimal;

@FunctionalInterface
public interface Discount {
    BigDecimal apply(BigDecimal price);
}
