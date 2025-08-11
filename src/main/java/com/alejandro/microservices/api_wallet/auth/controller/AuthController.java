package com.alejandro.microservices.api_wallet.auth.controller;

import com.alejandro.microservices.api_wallet.auth.dto.AuthRequest;
import com.alejandro.microservices.api_wallet.auth.dto.AuthResponse;
import com.alejandro.microservices.api_wallet.security.JwtUtil;
import com.alejandro.microservices.api_wallet.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Endpoints para autenticación y autorización")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario y devuelve un token JWT")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
            final String token = jwtUtil.generateToken(userDetails.getUsername());

            return ResponseEntity.ok(new AuthResponse(token, "Login exitoso"));
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body(new AuthResponse(null, "Credenciales inválidas"));
        }
    }

    @GetMapping("/test")
    @Operation(summary = "Test de autenticación", description = "Endpoint de prueba para verificar que la autenticación funciona")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Endpoint de autenticación funcionando correctamente");
    }
}
