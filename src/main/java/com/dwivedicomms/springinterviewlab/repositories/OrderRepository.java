package com.dwivedicomms.springinterviewlab.repositories;

import com.dwivedicomms.springinterviewlab.domain.Order;
import com.dwivedicomms.springinterviewlab.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerId(Long customerId);

    List<Order> findByStatus(StatusEnum status);

    @Query("SELECT o FROM Order o JOIN FETCH o.orderItemsList WHERE o.customer.id = :customerId")
    List<Order> findByCustomerIdWithItems(@Param("customerId") Long customerId);
}
