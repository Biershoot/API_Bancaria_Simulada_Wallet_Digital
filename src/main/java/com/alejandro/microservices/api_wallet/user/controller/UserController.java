package com.alejandro.microservices.api_wallet.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/user")
@Tag(name = "Usuario", description = "Endpoints de usuario (USER y ADMIN)")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    @GetMapping("/profile")
    @Operation(summary = "Perfil de usuario", description = "Obtiene el perfil del usuario autenticado")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> getUserProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return ResponseEntity.ok("Perfil del usuario: " + username);
    }

    @PutMapping("/profile")
    @Operation(summary = "Actualizar perfil", description = "Actualiza el perfil del usuario")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> updateUserProfile(@RequestBody String profileData) {
        return ResponseEntity.ok("Perfil actualizado exitosamente");
    }

    @GetMapping("/preferences")
    @Operation(summary = "Preferencias de usuario", description = "Obtiene las preferencias del usuario")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> getUserPreferences() {
        return ResponseEntity.ok("Preferencias del usuario");
    }

    @PostMapping("/preferences")
    @Operation(summary = "Actualizar preferencias", description = "Actualiza las preferencias del usuario")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> updateUserPreferences(@RequestBody String preferences) {
        return ResponseEntity.ok("Preferencias actualizadas exitosamente");
    }

    @GetMapping("/activity")
    @Operation(summary = "Actividad del usuario", description = "Obtiene el historial de actividad del usuario")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> getUserActivity() {
        return ResponseEntity.ok("Historial de actividad del usuario");
    }
}
