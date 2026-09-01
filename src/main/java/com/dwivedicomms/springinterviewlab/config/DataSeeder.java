package com.dwivedicomms.springinterviewlab.config;

import com.dwivedicomms.springinterviewlab.domain.Address;
import com.dwivedicomms.springinterviewlab.domain.Category;
import com.dwivedicomms.springinterviewlab.domain.Customer;
import com.dwivedicomms.springinterviewlab.domain.Product;
import com.dwivedicomms.springinterviewlab.repositories.CategoryRepository;
import com.dwivedicomms.springinterviewlab.repositories.CustomerRepository;
import com.dwivedicomms.springinterviewlab.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedData(CustomerRepository customerRepository,
                                CategoryRepository categoryRepository,
                                ProductRepository productRepository) {
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

            productRepository.save(product("Pixel 9", "Google flagship phone",
                    new BigDecimal("799.00"), 25, phones));
            productRepository.save(product("Galaxy S25", "Samsung flagship phone",
                    new BigDecimal("899.00"), 15, phones));
            productRepository.save(product("Effective Java", "Joshua Bloch's classic",
                    new BigDecimal("45.50"), 100, books));
        };
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
