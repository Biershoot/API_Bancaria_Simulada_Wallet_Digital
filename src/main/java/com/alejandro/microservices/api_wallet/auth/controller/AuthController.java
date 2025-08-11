package com.alejandro.microservices.api_wallet.auth.controller;

import com.alejandro.microservices.api_wallet.auth.dto.AuthRequest;
import com.alejandro.microservices.api_wallet.auth.dto.AuthResponse;
import com.alejandro.microservices.api_wallet.security.JwtTokenProvider;
import com.alejandro.microservices.api_wallet.security.MyUserDetailsService;
import com.alejandro.microservices.api_wallet.security.TokenBlacklistService;
import com.alejandro.microservices.api_wallet.wallet.entity.User;
import com.alejandro.microservices.api_wallet.wallet.repository.UserRepository;
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
import java.util.Map;
import java.time.Instant;

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

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario y devuelve access token y refresh token")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
            
            // Generar access token y refresh token
            final String accessToken = jwtTokenProvider.generarAccessToken(userDetails.getUsername());
            final String refreshToken = jwtTokenProvider.generarRefreshToken(userDetails.getUsername());

            // Guardar refresh token en la base de datos (si el usuario existe)
            try {
                User user = userRepository.findByEmail(userDetails.getUsername())
                        .orElse(null);
                if (user != null) {
                    user.setRefreshToken(refreshToken);
                    userRepository.save(user);
                }
            } catch (Exception e) {
                // Si no se puede guardar en BD, continuar sin refresh token
                System.err.println("Error guardando refresh token: " + e.getMessage());
            }

            return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken, "Login exitoso"));
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body(new AuthResponse(null, null, "Credenciales inválidas"));
        }
    }

    @GetMapping("/test")
    @Operation(summary = "Test de autenticación", description = "Endpoint de prueba para verificar que la autenticación funciona")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Endpoint de autenticación funcionando correctamente");
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refrescar token", description = "Genera un nuevo access token usando el refresh token")
    public ResponseEntity<AuthResponse> refresh(@RequestBody Map<String, String> request) {
        try {
            String refreshToken = request.get("refreshToken");
            
            if (refreshToken == null || refreshToken.isEmpty()) {
                return ResponseEntity.badRequest().body(new AuthResponse(null, null, "Refresh token requerido"));
            }

            // Validar el refresh token
            if (!jwtTokenProvider.validarToken(refreshToken)) {
                return ResponseEntity.badRequest().body(new AuthResponse(null, null, "Refresh token inválido"));
            }

            String username = jwtTokenProvider.obtenerUsernameDelToken(refreshToken);

            // Buscar usuario en la base de datos
            User user = userRepository.findByEmail(username)
                    .orElse(null);

            if (user == null || !refreshToken.equals(user.getRefreshToken())) {
                return ResponseEntity.badRequest().body(new AuthResponse(null, null, "Refresh token no válido para este usuario"));
            }

            // Generar nuevo access token
            String newAccessToken = jwtTokenProvider.generarAccessToken(username);

            return ResponseEntity.ok(new AuthResponse(newAccessToken, refreshToken, "Token refrescado exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new AuthResponse(null, null, "Error al refrescar token: " + e.getMessage()));
        }
    }

    @GetMapping("/blacklist/stats")
    @Operation(summary = "Estadísticas de blacklist", description = "Obtiene estadísticas de la lista negra de tokens")
    public ResponseEntity<String> getBlacklistStats() {
        long blacklistSize = tokenBlacklistService.getBlacklistSize();
        return ResponseEntity.ok("Tokens en blacklist: " + blacklistSize);
    }

    @PostMapping("/logout")
    @Operation(summary = "Cerrar sesión", description = "Cierra la sesión del usuario y registra el logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            
            try {
                // Obtener fecha de expiración del token
                Instant expiresAt = jwtTokenProvider.obtenerFechaExpiracionInstant(token);
                
                // Agregar token a la lista negra con su fecha de expiración
                tokenBlacklistService.blacklistToken(token, expiresAt);
                
                // Opcional: revocar refresh token del usuario
                try {
                    String username = jwtTokenProvider.obtenerUsernameDelToken(token);
                    User user = userRepository.findByEmail(username).orElse(null);
                    if (user != null && user.getRefreshToken() != null) {
                        user.setRefreshToken(null);
                        userRepository.save(user);
                    }
                } catch (Exception e) {
                    // Si no se puede revocar el refresh token, continuar
                    System.err.println("Error revocando refresh token: " + e.getMessage());
                }
                
                return ResponseEntity.ok("Logout exitoso. El token ha sido invalidado en el servidor.");
            } catch (Exception e) {
                // Si no se puede obtener la fecha de expiración, usar método legacy
                tokenBlacklistService.blacklistToken(token);
                return ResponseEntity.ok("Logout exitoso. El token ha sido invalidado en el servidor.");
            }
        }
        return ResponseEntity.badRequest().body("Token no encontrado");
    }
}
