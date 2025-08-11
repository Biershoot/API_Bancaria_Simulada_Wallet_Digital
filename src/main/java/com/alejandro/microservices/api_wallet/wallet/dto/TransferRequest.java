package com.alejandro.microservices.api_wallet.wallet.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {
    private String toEmail;
    private BigDecimal amount;
}
