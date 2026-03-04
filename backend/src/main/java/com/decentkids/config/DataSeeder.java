package com.decentkids.config;

import com.decentkids.entity.Admin;
import com.decentkids.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (adminRepository.count() == 0) {
            Admin admin = Admin.builder()
                    .username("admin")
                    .email("admin@decentkids.com")
                    .password(passwordEncoder.encode("Admin@123"))
                    .active(true)
                    .build();
            adminRepository.save(admin);
            log.info("Default admin created — username: admin, password: Admin@123");
            log.info("⚠️  Please change the default admin password after first login!");
        }
    }
}
