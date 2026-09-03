package com.dwivedicomms.springinterviewlab.repositories;

import com.dwivedicomms.springinterviewlab.domain.OrderItems;
import com.dwivedicomms.springinterviewlab.dto.CategoryRevenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {

    List<OrderItems> findByOrderId(Long orderId);

    @Query("SELECT p.category.name, SUM(oi.priceAtPurchase * oi.quantity) " +
            "FROM OrderItems oi JOIN oi.product p " +
            "GROUP BY p.category.name " +
            "HAVING SUM(oi.priceAtPurchase * oi.quantity) > :minRevenue")
    List<Object[]> findCategoryRevenueAbove(@Param("minRevenue") BigDecimal minRevenue);

    @Query("SELECT new com.dwivedicomms.springinterviewlab.dto.CategoryRevenue(p.category.name, SUM(oi.priceAtPurchase * oi.quantity)) " +
            "FROM OrderItems oi JOIN oi.product p " +
            "GROUP BY p.category.name " +
            "HAVING SUM(oi.priceAtPurchase * oi.quantity) > :minRevenue")
    List<CategoryRevenue> findCategoryRevenueAboveAsDto(@Param("minRevenue") BigDecimal minRevenue);
}
