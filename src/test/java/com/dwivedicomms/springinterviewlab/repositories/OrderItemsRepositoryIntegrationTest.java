package com.dwivedicomms.springinterviewlab.repositories;

import com.dwivedicomms.springinterviewlab.domain.Category;
import com.dwivedicomms.springinterviewlab.domain.Order;
import com.dwivedicomms.springinterviewlab.domain.OrderItems;
import com.dwivedicomms.springinterviewlab.domain.Product;
import com.dwivedicomms.springinterviewlab.dto.CategoryRevenue;
import com.dwivedicomms.springinterviewlab.enums.StatusEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderItemsRepositoryIntegrationTest {

    @Autowired
    OrderItemsRepository orderItemsRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    private OrderItems addItem(Order order, Product product, int quantity, String price) {
        OrderItems item = new OrderItems();
        item.setOrder(order);
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setPriceAtPurchase(new BigDecimal(price));
        return orderItemsRepository.save(item);
    }

    @Test
    void findByOrderId_returnsOnlyItemsBelongingToThatOrder() {
        Category electronics = new Category();
        electronics.setName("Electronics");
        categoryRepository.save(electronics);

        Product phone = new Product();
        phone.setName("Phone");
        phone.setPrice(new BigDecimal("500.00"));
        phone.setCategory(electronics);
        productRepository.save(phone);

        Order orderOne = new Order();
        orderOne.setStatus(StatusEnum.CONFIRMED);
        orderRepository.save(orderOne);

        Order orderTwo = new Order();
        orderTwo.setStatus(StatusEnum.PENDING);
        orderRepository.save(orderTwo);

        addItem(orderOne, phone, 1, "500.00");
        addItem(orderOne, phone, 2, "500.00");
        addItem(orderTwo, phone, 1, "500.00");

        List<OrderItems> itemsForOrderOne = orderItemsRepository.findByOrderId(orderOne.getId());

        assertThat(itemsForOrderOne).hasSize(2);
        assertThat(itemsForOrderOne).allSatisfy(item -> assertThat(item.getOrder().getId()).isEqualTo(orderOne.getId()));
    }

    @Test
    void findCategoryRevenueAboveAsDto_returnsOnlyCategoriesPastTheThreshold() {
        Category electronics = new Category();
        electronics.setName("Electronics");
        categoryRepository.save(electronics);

        Category books = new Category();
        books.setName("Books");
        categoryRepository.save(books);

        Product laptop = new Product();
        laptop.setName("Laptop");
        laptop.setPrice(new BigDecimal("1000.00"));
        laptop.setCategory(electronics);
        productRepository.save(laptop);

        Product novel = new Product();
        novel.setName("Novel");
        novel.setPrice(new BigDecimal("20.00"));
        novel.setCategory(books);
        productRepository.save(novel);

        Order order = new Order();
        order.setStatus(StatusEnum.CONFIRMED);
        orderRepository.save(order);

        addItem(order, laptop, 2, "1000.00");
        addItem(order, novel, 1, "20.00");

        List<CategoryRevenue> aboveThreshold = orderItemsRepository.findCategoryRevenueAboveAsDto(new BigDecimal("500.00"));

        assertThat(aboveThreshold)
                .extracting(CategoryRevenue::categoryName)
                .containsExactly("Electronics");
    }
}
