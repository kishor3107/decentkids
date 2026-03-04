package com.decentkids.dto;

import com.decentkids.entity.Product;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductDto {

    @Data
    public static class CreateRequest {
        @NotBlank(message = "Product name is required")
        private String name;

        private String description;

        @NotNull(message = "Price is required")
        @DecimalMin("0.01")
        private BigDecimal price;

        @NotNull(message = "Stock is required")
        @Min(0)
        private Integer stock;

        private String imageUrl;
        private Product.Category category;
        private Product.AgeGroup ageGroup;
        private String sizes;
    }

    @Data
    public static class UpdateRequest {
        private String name;
        private String description;
        private BigDecimal price;
        private Integer stock;
        private String imageUrl;
        private Product.Category category;
        private Product.AgeGroup ageGroup;
        private String sizes;
        private Boolean active;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String name;
        private String description;
        private BigDecimal price;
        private Integer stock;
        private String imageUrl;
        private Product.Category category;
        private Product.AgeGroup ageGroup;
        private String sizes;
        private boolean active;
        private LocalDateTime createdAt;
    }
}