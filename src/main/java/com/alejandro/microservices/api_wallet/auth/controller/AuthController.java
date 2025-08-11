package com.alejandro.microservices.api_wallet.auth.controller;

import com.alejandro.microservices.api_wallet.auth.dto.AuthRequest;
import com.alejandro.microservices.api_wallet.auth.dto.AuthResponse;
import com.alejandro.microservices.api_wallet.security.JwtTokenProvider;
import com.alejandro.microservices.api_wallet.security.MyUserDetailsService;
import com.alejandro.microservices.api_wallet.security.TokenBlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
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
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario y devuelve un token JWT")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
            final String token = jwtTokenProvider.generarToken(userDetails.getUsername());

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

    @PostMapping("/logout")
    @Operation(summary = "Cerrar sesión", description = "Cierra la sesión del usuario y registra el logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            // Agregar token a la lista negra para invalidarlo en el servidor
            tokenBlacklistService.blacklistToken(token);
            return ResponseEntity.ok("Logout exitoso. El token ha sido invalidado en el servidor.");
        }
        return ResponseEntity.badRequest().body("Token no encontrado");
    }
}
