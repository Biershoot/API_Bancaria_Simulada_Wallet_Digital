package com.alejandro.microservices.api_wallet.wallet.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@ToString(exclude = {"walletFrom", "walletTo"})
@EqualsAndHashCode(exclude = {"walletFrom", "walletTo"})
@NoArgsConstructor
public class Transaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_from")
    private Wallet walletFrom; // nullable for DEPOSIT

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_to")
    private Wallet walletTo;   // nullable for WITHDRAW

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    private Instant createdAt = Instant.now();

    private String description;

    @Column(unique = true)
    private String clientTransferId; // optional for idempotency

    public enum TransactionType {
        DEPOSIT, WITHDRAWAL, TRANSFER
    }

    public enum TransactionStatus {
        PENDING, SUCCESS, FAILED
    }

    public Transaction(Wallet walletFrom, Wallet walletTo, BigDecimal amount, TransactionType type) {
        this.walletFrom = walletFrom;
        this.walletTo = walletTo;
        this.amount = amount;
        this.type = type;
        this.status = TransactionStatus.PENDING;
    }
}
