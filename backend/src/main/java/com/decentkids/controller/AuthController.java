package com.decentkids.controller;

import com.decentkids.dto.ApiResponse;
import com.decentkids.dto.AuthDto.*;
import com.decentkids.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Register and login endpoints")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/customer/register")
    @Operation(summary = "Register a new customer")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody CustomerRegisterRequest request) {
        AuthResponse response = authService.registerCustomer(request);
        return ResponseEntity.ok(ApiResponse.success("Registration successful", response));
    }

    @PostMapping("/customer/login")
    @Operation(summary = "Customer login")
    public ResponseEntity<ApiResponse<AuthResponse>> customerLogin(@Valid @RequestBody CustomerLoginRequest request) {
        AuthResponse response = authService.loginCustomer(request);
        return ResponseEntity.ok(ApiResponse.success("Login successful", response));
    }

    @PostMapping("/admin/login")
    @Operation(summary = "Admin login")
    public ResponseEntity<ApiResponse<AuthResponse>> adminLogin(@Valid @RequestBody AdminLoginRequest request) {
        AuthResponse response = authService.loginAdmin(request);
        return ResponseEntity.ok(ApiResponse.success("Admin login successful", response));
    }
}
