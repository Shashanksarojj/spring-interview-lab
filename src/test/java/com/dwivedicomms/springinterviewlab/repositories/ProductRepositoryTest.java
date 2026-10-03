package com.dwivedicomms.springinterviewlab.repositories;

import com.dwivedicomms.springinterviewlab.domain.Category;
import com.dwivedicomms.springinterviewlab.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void findsProductsAboveAveragePrice() {
        Category electronics = new Category();
        electronics.setName("Electronics");
        categoryRepository.save(electronics);

        Product cheap = new Product();
        cheap.setName("Cheap Thing");
        cheap.setPrice(new BigDecimal("10.00"));
        cheap.setCategory(electronics);
        productRepository.save(cheap);

        Product expensive = new Product();
        expensive.setName("Expensive Thing");
        expensive.setPrice(new BigDecimal("1000.00"));
        expensive.setCategory(electronics);
        productRepository.save(expensive);

        List<Product> aboveAverage = productRepository.findPricedAboveAverage();

        assertThat(aboveAverage).extracting(Product::getName).containsExactly("Expensive Thing");
    }
}
