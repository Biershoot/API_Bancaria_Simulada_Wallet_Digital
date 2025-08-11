package com.alejandro.microservices.api_wallet.wallet.repository;

import com.alejandro.microservices.api_wallet.wallet.entity.User;
import com.alejandro.microservices.api_wallet.wallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByUser(User user);
}
