package com.alejandro.microservices.api_wallet.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponse {
    private String message;
    private String fromEmail;
    private String toEmail;
    private BigDecimal amount;
    private Instant timestamp;
}
