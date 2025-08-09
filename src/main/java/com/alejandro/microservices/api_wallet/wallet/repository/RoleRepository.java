package com.alejandro.microservices.api_wallet.wallet.repository;

import com.alejandro.microservices.api_wallet.wallet.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(Role.RoleName name);
}
