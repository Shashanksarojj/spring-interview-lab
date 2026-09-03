package com.dwivedicomms.springinterviewlab.config;

import com.dwivedicomms.springinterviewlab.domain.Address;
import com.dwivedicomms.springinterviewlab.domain.Category;
import com.dwivedicomms.springinterviewlab.domain.Customer;
import com.dwivedicomms.springinterviewlab.domain.Order;
import com.dwivedicomms.springinterviewlab.domain.OrderItems;
import com.dwivedicomms.springinterviewlab.domain.Payment;
import com.dwivedicomms.springinterviewlab.domain.Product;
import com.dwivedicomms.springinterviewlab.enums.PaymentMethodEnum;
import com.dwivedicomms.springinterviewlab.enums.PaymentStatusEnum;
import com.dwivedicomms.springinterviewlab.enums.StatusEnum;
import com.dwivedicomms.springinterviewlab.repositories.CategoryRepository;
import com.dwivedicomms.springinterviewlab.repositories.CustomerRepository;
import com.dwivedicomms.springinterviewlab.repositories.OrderRepository;
import com.dwivedicomms.springinterviewlab.repositories.PaymentRepository;
import com.dwivedicomms.springinterviewlab.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedData(CustomerRepository customerRepository,
                                CategoryRepository categoryRepository,
                                ProductRepository productRepository,
                                OrderRepository orderRepository,
                                PaymentRepository paymentRepository) {
        return args -> {
            Customer alice = new Customer();
            alice.setFirstName("Alice");
            alice.setLastName("Nguyen");
            alice.setEmail("alice@example.com");
            alice.setPhone(9876543210L);
            addAddress(alice, "12 MG Road", "Bengaluru", "560001");
            addAddress(alice, "45 Residency Rd", "Bengaluru", "560025");
            customerRepository.save(alice);

            Customer bob = new Customer();
            bob.setFirstName("Bob");
            bob.setLastName("Fernandes");
            bob.setEmail("bob@example.com");
            bob.setPhone(9123456780L);
            addAddress(bob, "7 Marine Drive", "Mumbai", "400002");
            customerRepository.save(bob);

            Category electronics = new Category();
            electronics.setName("Electronics");
            categoryRepository.save(electronics);

            Category phones = new Category();
            phones.setName("Phones");
            phones.setParent(electronics);
            categoryRepository.save(phones);
            electronics.getChildren().add(phones);

            Category books = new Category();
            books.setName("Books");
            categoryRepository.save(books);

            Product pixel9 = productRepository.save(product("Pixel 9", "Google flagship phone",
                    new BigDecimal("799.00"), 25, phones));
            Product galaxyS25 = productRepository.save(product("Galaxy S25", "Samsung flagship phone",
                    new BigDecimal("899.00"), 15, phones));
            Product effectiveJava = productRepository.save(product("Effective Java", "Joshua Bloch's classic",
                    new BigDecimal("45.50"), 100, books));

            Order aliceOrder = new Order();
            aliceOrder.setCustomer(alice);
            aliceOrder.setStatus(StatusEnum.CONFIRMED);
            addOrderItem(aliceOrder, pixel9, 1, pixel9.getPrice());
            addOrderItem(aliceOrder, effectiveJava, 2, effectiveJava.getPrice());
            aliceOrder = orderRepository.save(aliceOrder);

            Payment alicePayment = new Payment();
            alicePayment.setOrder(aliceOrder);
            alicePayment.setAmount(orderTotal(aliceOrder));
            alicePayment.setMethod(PaymentMethodEnum.CARD);
            alicePayment.setStatus(PaymentStatusEnum.SUCCESS);
            alicePayment.setPaidAt(Instant.now());
            paymentRepository.save(alicePayment);

            Order bobOrder = new Order();
            bobOrder.setCustomer(bob);
            bobOrder.setStatus(StatusEnum.PENDING);
            addOrderItem(bobOrder, galaxyS25, 1, galaxyS25.getPrice());
            bobOrder = orderRepository.save(bobOrder);

            Payment bobPayment = new Payment();
            bobPayment.setOrder(bobOrder);
            bobPayment.setAmount(orderTotal(bobOrder));
            bobPayment.setMethod(PaymentMethodEnum.UPI);
            bobPayment.setStatus(PaymentStatusEnum.PENDING);
            paymentRepository.save(bobPayment);
        };
    }

    private void addOrderItem(Order order, Product product, Integer quantity, BigDecimal priceAtPurchase) {
        if (order.getOrderItemsList() == null) {
            order.setOrderItemsList(new ArrayList<>());
        }
        OrderItems item = new OrderItems();
        item.setOrder(order);
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setPriceAtPurchase(priceAtPurchase);
        order.getOrderItemsList().add(item);
    }

    private BigDecimal orderTotal(Order order) {
        return order.getOrderItemsList().stream()
                .map(item -> item.getPriceAtPurchase().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void addAddress(Customer customer, String street, String city, String pinCode) {
        Address address = new Address();
        address.setStreet(street);
        address.setCity(city);
        address.setPinCode(pinCode);
        address.setCustomer(customer);
        customer.getAddresses().add(address);
    }

    private Product product(String name, String description, BigDecimal price,
                             Integer stockQuantity, Category category) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStockQuantity(stockQuantity);
        product.setCategory(category);
        return product;
    }
}
