package com.dwivedicomms.springinterviewlab.repositories;

import com.dwivedicomms.springinterviewlab.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryId(Long categoryId);

    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

    List<Product> findByPriceLessThan(BigDecimal price);

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId OR p.category.parent.id = :categoryId")
    List<Product> findByCategoryIncludingSubcategories(@Param("categoryId") Long categoryId);

    @Query("SELECT p FROM Product p WHERE p.price > (SELECT AVG(p2.price) FROM Product p2)")
    List<Product> findPricedAboveAverage();

    @Query(value = "SELECT * FROM product WHERE price > (SELECT AVG(price) FROM product)", nativeQuery = true)
    List<Product> findPricedAboveAverageNative();
}
