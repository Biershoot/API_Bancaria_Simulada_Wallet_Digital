package com.alejandro.microservices.api_wallet.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Administración", description = "Endpoints de administración (solo ADMIN)")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    @GetMapping("/dashboard")
    @Operation(summary = "Dashboard de administración", description = "Acceso al panel de administración")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> getAdminDashboard() {
        return ResponseEntity.ok("Panel de administración - Solo accesible para administradores");
    }

    @GetMapping("/users")
    @Operation(summary = "Listar usuarios", description = "Obtiene la lista de todos los usuarios")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> getAllUsers() {
        return ResponseEntity.ok("Lista de usuarios del sistema");
    }

    @PostMapping("/users/{userId}/disable")
    @Operation(summary = "Deshabilitar usuario", description = "Deshabilita un usuario específico")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> disableUser(@PathVariable String userId) {
        return ResponseEntity.ok("Usuario " + userId + " deshabilitado exitosamente");
    }

    @GetMapping("/statistics")
    @Operation(summary = "Estadísticas del sistema", description = "Obtiene estadísticas del sistema")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> getSystemStatistics() {
        return ResponseEntity.ok("Estadísticas del sistema - Total de usuarios: 150, Transacciones: 1250");
    }
}
