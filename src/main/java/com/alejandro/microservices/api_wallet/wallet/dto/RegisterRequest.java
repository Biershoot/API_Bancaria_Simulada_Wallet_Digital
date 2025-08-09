package com.alejandro.microservices.api_wallet.wallet.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
}


