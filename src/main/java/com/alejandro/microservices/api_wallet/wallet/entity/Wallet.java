package com.alejandro.microservices.api_wallet.wallet.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "wallets")
@Getter
@Setter
@ToString(exclude = "user")
@EqualsAndHashCode(exclude = "user")
@NoArgsConstructor
public class Wallet {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    private String currency = "COP";

    private Instant createdAt = Instant.now();

    public Wallet(User user, String currency) {
        this.user = user;
        this.currency = currency;
    }
}
