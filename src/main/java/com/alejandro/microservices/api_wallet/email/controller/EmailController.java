package com.alejandro.microservices.api_wallet.email.controller;

import com.alejandro.microservices.api_wallet.email.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@Tag(name = "Email", description = "Endpoints para pruebas de envío de correos")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/test")
    @Operation(summary = "Enviar correo de prueba", description = "Envía un correo de prueba simple")
    public ResponseEntity<String> enviarCorreoPrueba(@RequestParam String email) {
        try {
            emailService.enviarCorreoSimple(
                email,
                "Prueba de Email - Wallet Digital",
                """
                <html>
                <body>
                    <h2>Prueba de Email</h2>
                    <p>Este es un correo de prueba del sistema de Wallet Digital.</p>
                    <p>Si recibes este correo, significa que la configuración de email está funcionando correctamente.</p>
                    <br>
                    <p>Fecha y hora: <strong>%s</strong></p>
                </body>
                </html>
                """.formatted(java.time.LocalDateTime.now())
            );
            return ResponseEntity.ok("Correo de prueba enviado exitosamente a " + email);
        } catch (MessagingException e) {
            return ResponseEntity.badRequest().body("Error enviando correo: " + e.getMessage());
        }
    }

    @PostMapping("/test-transfer")
    @Operation(summary = "Simular notificación de transferencia", description = "Envía un correo simulando una notificación de transferencia")
    public ResponseEntity<String> simularNotificacionTransferencia(
            @RequestParam String emailDestinatario,
            @RequestParam String emailRemitente,
            @RequestParam double monto) {
        try {
            emailService.enviarNotificacionTransferencia(emailDestinatario, emailRemitente, monto);
            return ResponseEntity.ok("Notificación de transferencia enviada exitosamente");
        } catch (MessagingException e) {
            return ResponseEntity.badRequest().body("Error enviando notificación: " + e.getMessage());
        }
    }
}
