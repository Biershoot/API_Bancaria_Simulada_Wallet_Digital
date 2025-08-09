package com.alejandro.microservices.api_wallet.wallet.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}


