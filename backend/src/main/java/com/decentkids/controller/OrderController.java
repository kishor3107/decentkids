package com.decentkids.controller;

import com.decentkids.dto.ApiResponse;
import com.decentkids.entity.Order;
import com.decentkids.service.OrderService;
import com.decentkids.service.OrderService.OrderItemRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Orders", description = "Order management endpoints")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/customer/{customerId}")
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @Operation(summary = "Place a new order")
    public ResponseEntity<ApiResponse<Order>> placeOrder(
            @PathVariable Long customerId,
            @Valid @RequestBody PlaceOrderRequest request) {

        List<OrderItemRequest> items = request.getItems().stream()
                .map(i -> new OrderItemRequest(i.getProductId(), i.getQuantity(), i.getSize()))
                .toList();

        Order order = orderService.placeOrder(customerId, items, request.getShippingAddress(), request.getPaymentMethod());
        return ResponseEntity.ok(ApiResponse.success("Order placed successfully", order));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @Operation(summary = "Place a new order (frontend compatible)")
    public ResponseEntity<ApiResponse<Order>> placeOrderAlt(@Valid @RequestBody PlaceOrderRequestAlt request) {
        List<OrderService.OrderItemRequest> items = request.getOrderItems().stream()
                .map(i -> new OrderService.OrderItemRequest(i.getProductId(), i.getQuantity(), "M")) // Default size
                .toList();

        Order order = orderService.placeOrder(
            request.getCustomerId(), 
            items, 
            request.getNotes() != null ? request.getNotes() : "No special instructions", 
            "ONLINE"
        );
        return ResponseEntity.ok(ApiResponse.success("Order placed successfully", order));
    }

    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @Operation(summary = "Get all orders for a customer")
    public ResponseEntity<ApiResponse<List<Order>>> getCustomerOrders(@PathVariable Long customerId) {
        return ResponseEntity.ok(ApiResponse.success(orderService.getOrdersByCustomer(customerId)));
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @Operation(summary = "Get order by ID")
    public ResponseEntity<ApiResponse<Order>> getOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(ApiResponse.success(orderService.getOrderById(orderId)));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all orders [Admin only]")
    public ResponseEntity<ApiResponse<Page<Order>>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<Order> orders = orderService.getAllOrders(PageRequest.of(page, size, Sort.by("createdAt").descending()));
        return ResponseEntity.ok(ApiResponse.success(orders));
    }

    @PatchMapping("/{orderId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update order status [Admin only]")
    public ResponseEntity<ApiResponse<Order>> updateStatus(
            @PathVariable Long orderId,
            @RequestParam Order.OrderStatus status) {
        return ResponseEntity.ok(ApiResponse.success("Status updated", orderService.updateOrderStatus(orderId, status)));
    }

    // ─── Inner request DTOs ──────────────────────────────────────────────

    @Data
    public static class PlaceOrderRequest {
        private List<ItemRequest> items;
        private String shippingAddress;
        private String paymentMethod;
    }

    @Data
    public static class ItemRequest {
        private Long productId;
        private Integer quantity;
        private String size;
    }

    @Data
    public static class PlaceOrderRequestAlt {
        private Long customerId;
        private List<OrderItemDto> orderItems;
        private java.math.BigDecimal total;
        private java.math.BigDecimal deliveryCharge;
        private java.math.BigDecimal discount;
        private String couponCode;
        private String notes;
    }

    @Data
    public static class OrderItemDto {
        private Long productId;
        private Integer quantity;
        private java.math.BigDecimal price;
    }
}
