package com.dwivedicomms.springinterviewlab.java8lab;

import java.util.List;

public record LabOrder(String customerName, List<Item> items) {
}
