package com.alejandro.microservices.api_wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ApiWalletApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiWalletApplication.class, args);
    }
}
