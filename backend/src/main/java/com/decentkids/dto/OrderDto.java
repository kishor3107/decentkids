package com.decentkids.dto;

import com.decentkids.entity.Order;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {

    @Data
    public static class CreateRequest {
        @NotNull
        private List<OrderItemRequest> items;
        private String shippingAddress;
        private String paymentMethod;
    }

    @Data
    public static class OrderItemRequest {
        @NotNull
        private Long productId;

        @Min(1)
        private Integer quantity;

        private String size;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private Long customerId;
        private String customerName;
        private List<OrderItemResponse> items;
        private BigDecimal totalAmount;
        private Order.OrderStatus status;
        private String shippingAddress;
        private String paymentMethod;
        private LocalDateTime createdAt;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemResponse {
        private Long productId;
        private String productName;
        private String productImage;
        private Integer quantity;
        private String size;
        private BigDecimal priceAtPurchase;
    }
}
