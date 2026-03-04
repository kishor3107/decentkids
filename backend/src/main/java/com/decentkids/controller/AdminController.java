package com.decentkids.controller;

import com.decentkids.dto.ApiResponse;
import com.decentkids.repository.*;
import com.decentkids.entity.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Admin Dashboard", description = "Admin-only management endpoints")
public class AdminController {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @GetMapping("/dashboard")
    @Operation(summary = "Get dashboard stats")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDashboard() {
        Map<String, Object> stats = new HashMap<>();

        stats.put("totalCustomers", customerRepository.count());
        stats.put("totalProducts", productRepository.countByActiveTrue());
        stats.put("totalOrders", orderRepository.count());
        stats.put("pendingOrders", orderRepository.countByStatus(Order.OrderStatus.PENDING));
        stats.put("deliveredOrders", orderRepository.countByStatus(Order.OrderStatus.DELIVERED));
        stats.put("ordersThisMonth", orderRepository.countOrdersThisMonth());

        BigDecimal revenue = orderRepository.totalRevenue();
        stats.put("totalRevenue", revenue != null ? revenue : BigDecimal.ZERO);

        List<com.decentkids.entity.Product> lowStock = productRepository.findLowStockProducts(5);
        stats.put("lowStockProducts", lowStock.size());
        stats.put("lowStockItems", lowStock.stream().map(p -> Map.of(
                "id", p.getId(), "name", p.getName(), "stock", p.getStock()
        )).toList());

        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    @GetMapping("/customers")
    @Operation(summary = "Get all customers")
    public ResponseEntity<ApiResponse<?>> getAllCustomers() {
        return ResponseEntity.ok(ApiResponse.success(customerRepository.findAll()));
    }

    @DeleteMapping("/customers/{id}")
    @Operation(summary = "Deactivate customer account")
    public ResponseEntity<ApiResponse<Void>> deactivateCustomer(@PathVariable Long id) {
        customerRepository.findById(id).ifPresent(c -> {
            c.setActive(false);
            customerRepository.save(c);
        });
        return ResponseEntity.ok(ApiResponse.success("Customer deactivated", null));
    }
}
