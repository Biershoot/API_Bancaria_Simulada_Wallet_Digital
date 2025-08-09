package com.alejandro.microservices.api_wallet.wallet.config;

import com.alejandro.microservices.api_wallet.wallet.entity.Role;
import com.alejandro.microservices.api_wallet.wallet.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        if (roleRepository.findByName(Role.RoleName.ROLE_USER).isEmpty()) {
            roleRepository.save(new Role(Role.RoleName.ROLE_USER));
        }
        if (roleRepository.findByName(Role.RoleName.ROLE_ADMIN).isEmpty()) {
            roleRepository.save(new Role(Role.RoleName.ROLE_ADMIN));
        }
    }
}


