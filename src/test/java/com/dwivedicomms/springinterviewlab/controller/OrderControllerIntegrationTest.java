package com.dwivedicomms.springinterviewlab.controller;

import com.dwivedicomms.springinterviewlab.domain.Category;
import com.dwivedicomms.springinterviewlab.domain.Order;
import com.dwivedicomms.springinterviewlab.domain.OrderItems;
import com.dwivedicomms.springinterviewlab.domain.Product;
import com.dwivedicomms.springinterviewlab.enums.StatusEnum;
import com.dwivedicomms.springinterviewlab.repositories.CategoryRepository;
import com.dwivedicomms.springinterviewlab.repositories.OrderRepository;
import com.dwivedicomms.springinterviewlab.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.autoconfigure.exclude="
                        + "org.springframework.boot.security.autoconfigure.web.servlet.SecurityFilterAutoConfiguration,"
                        + "org.springframework.boot.security.autoconfigure.web.servlet.ServletWebSecurityAutoConfiguration,"
                        + "org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration",
                "spring.datasource.url=jdbc:h2:mem:orderControllerIntegrationTest;DB_CLOSE_DELAY=-1"
        })
@AutoConfigureTestRestTemplate
class OrderControllerIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void getOrderTotal_computesRealTotalThroughTheFullStack() {
        Category electronics = new Category();
        electronics.setName("Electronics");
        categoryRepository.save(electronics);

        Product keyboard = new Product();
        keyboard.setName("Mechanical Keyboard");
        keyboard.setPrice(new BigDecimal("150.00"));
        keyboard.setCategory(electronics);
        productRepository.save(keyboard);

        Order order = new Order();
        order.setStatus(StatusEnum.CONFIRMED);

        OrderItems item = new OrderItems();
        item.setOrder(order);
        item.setProduct(keyboard);
        item.setQuantity(3);
        item.setPriceAtPurchase(new BigDecimal("150.00"));
        order.getOrderItemsList().add(item);

        order = orderRepository.save(order);

        ResponseEntity<BigDecimal> response = restTemplate.getForEntity(
                "/api/orders/{id}/total", BigDecimal.class, order.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualByComparingTo("450.00");
    }

    @Test
    void getOrderTotal_forUnknownOrderCurrentlySurfacesAsServerError() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/api/orders/{id}/total", String.class, Long.MAX_VALUE);

        assertThat(response.getStatusCode().is5xxServerError()).isTrue();
    }
}
