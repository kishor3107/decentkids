package com.decentkids.service;

import com.decentkids.dto.AuthDto.*;
import com.decentkids.entity.Admin;
import com.decentkids.entity.Customer;
import com.decentkids.exception.BadRequestException;
import com.decentkids.exception.ResourceNotFoundException;
import com.decentkids.exception.UnauthorizedException;
import com.decentkids.repository.AdminRepository;
import com.decentkids.repository.CustomerRepository;
import com.decentkids.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final CustomerRepository customerRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public AuthResponse registerCustomer(CustomerRegisterRequest request) {
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already registered: " + request.getEmail());
        }

        Customer customer = Customer.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .address(request.getAddress())
                .age(request.getAge())
                .gender(request.getGender())
                .build();

        customerRepository.save(customer);

        String token = jwtTokenProvider.generateToken(customer.getEmail(), "CUSTOMER");

        return AuthResponse.builder()
                .token(token)
                .role("CUSTOMER")
                .userId(customer.getId())
                .name(customer.getFullName())
                .email(customer.getEmail())
                .build();
    }

    public AuthResponse loginCustomer(CustomerLoginRequest request) {
        Customer customer = customerRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), customer.getPassword())) {
            throw new UnauthorizedException("Invalid email or password");
        }

        if (!customer.isActive()) {
            throw new UnauthorizedException("Account is deactivated. Please contact support.");
        }

        String token = jwtTokenProvider.generateToken(customer.getEmail(), "CUSTOMER");

        return AuthResponse.builder()
                .token(token)
                .role("CUSTOMER")
                .userId(customer.getId())
                .name(customer.getFullName())
                .email(customer.getEmail())
                .build();
    }

    public AuthResponse loginAdmin(AdminLoginRequest request) {
        Admin admin = adminRepository.findByUsernameOrEmail(
                        request.getUsernameOrEmail(), request.getUsernameOrEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new UnauthorizedException("Invalid credentials");
        }

        if (!admin.isActive()) {
            throw new UnauthorizedException("Admin account is deactivated");
        }

        String token = jwtTokenProvider.generateToken(admin.getUsername(), "ADMIN");

        return AuthResponse.builder()
                .token(token)
                .role("ADMIN")
                .userId(admin.getId())
                .name(admin.getUsername())
                .email(admin.getEmail())
                .build();
    }
}
