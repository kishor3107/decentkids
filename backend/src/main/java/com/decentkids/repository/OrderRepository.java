package com.decentkids.repository;

import com.decentkids.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerIdOrderByCreatedAtDesc(Long customerId);

    Page<Order> findByStatus(Order.OrderStatus status, Pageable pageable);

    long countByStatus(Order.OrderStatus status);

    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.status = 'DELIVERED'")
    BigDecimal totalRevenue();

    @Query("SELECT COUNT(o) FROM Order o WHERE MONTH(o.createdAt) = MONTH(CURRENT_DATE) AND YEAR(o.createdAt) = YEAR(CURRENT_DATE)")
    long countOrdersThisMonth();
}
