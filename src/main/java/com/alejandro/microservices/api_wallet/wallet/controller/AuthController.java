package com.alejandro.microservices.api_wallet.wallet.controller;

import com.alejandro.microservices.api_wallet.wallet.dto.AuthRequest;
import com.alejandro.microservices.api_wallet.wallet.dto.AuthResponse;
import com.alejandro.microservices.api_wallet.wallet.dto.RegisterRequest;
import com.alejandro.microservices.api_wallet.wallet.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}


