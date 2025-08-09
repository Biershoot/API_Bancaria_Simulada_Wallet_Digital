package com.alejandro.microservices.api_wallet.wallet.repository;

import com.alejandro.microservices.api_wallet.wallet.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findByWalletFromIdOrWalletToId(Long fromId, Long toId, Pageable pageable);
}
