package com.decentkids.dto;

import com.decentkids.entity.Customer;
import jakarta.validation.constraints.*;
import lombok.*;

// ─── Auth Request DTOs ───────────────────────────────────────────────

public class AuthDto {

    @Data
    public static class CustomerRegisterRequest {
        @NotBlank(message = "Full name is required")
        private String fullName;

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        private String email;

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        private String password;

        private String phone;
        private String address;
        private Integer age;
        private Customer.Gender gender;
    }

    @Data
    public static class CustomerLoginRequest {
        @NotBlank(message = "Email is required")
        @Email
        private String email;

        @NotBlank(message = "Password is required")
        private String password;
    }

    @Data
    public static class AdminLoginRequest {
        @NotBlank(message = "Username or email is required")
        private String usernameOrEmail;

        @NotBlank(message = "Password is required")
        private String password;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AuthResponse {
        private String token;
        private String role;
        private Long userId;
        private String name;
        private String email;
    }
}
