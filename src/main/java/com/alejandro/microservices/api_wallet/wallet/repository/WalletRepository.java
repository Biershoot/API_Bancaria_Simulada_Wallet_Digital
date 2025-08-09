package com.alejandro.microservices.api_wallet.wallet.repository;

import com.alejandro.microservices.api_wallet.wallet.entity.Wallet;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import java.util.List;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select w from Wallet w where w.id = :id")
    Optional<Wallet> findByIdForUpdate(@Param("id") Long id);

    List<Wallet> findByUserId(Long userId);
}
