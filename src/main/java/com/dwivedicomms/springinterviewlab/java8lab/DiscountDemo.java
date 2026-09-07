package com.dwivedicomms.springinterviewlab.java8lab;


import java.math.BigDecimal;

public class DiscountDemo {
    public static void main(String[] args){
        Discount tenPercentOff = price -> price.multiply(new BigDecimal("0.90"));
        Discount flatTenOff = price -> price.subtract(BigDecimal.TEN);

        BigDecimal price = new BigDecimal("100.00");
        System.out.println("10% off:" + tenPercentOff.apply(price));
        System.out.println("Flat $10 off: " + flatTenOff.apply(price));
    }
}
