package com.alejandro.microservices.api_wallet.email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Envía un correo con archivo adjunto
     */
    public void enviarCorreoConAdjunto(String para, String asunto, String cuerpo, String rutaArchivo, String nombreArchivo) throws MessagingException {
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);

        helper.setTo(para);
        helper.setSubject(asunto);
        helper.setText(cuerpo, true); // true para HTML

        FileSystemResource archivo = new FileSystemResource(new File(rutaArchivo));
        helper.addAttachment(nombreArchivo, archivo);

        mailSender.send(mensaje);
    }

    /**
     * Envía un correo simple sin adjuntos
     */
    public void enviarCorreoSimple(String para, String asunto, String cuerpo) throws MessagingException {
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);

        helper.setTo(para);
        helper.setSubject(asunto);
        helper.setText(cuerpo, true); // true para HTML

        mailSender.send(mensaje);
    }

    /**
     * Envía notificación de transferencia
     */
    public void enviarNotificacionTransferencia(String emailDestinatario, String emailRemitente, double monto) throws MessagingException {
        String asunto = "Transferencia recibida - Wallet Digital";
        String cuerpo = String.format("""
            <html>
            <body>
                <h2>Transferencia Recibida</h2>
                <p>Has recibido una transferencia de <strong>$%.2f</strong> desde <strong>%s</strong>.</p>
                <p>Tu saldo ha sido actualizado automáticamente.</p>
                <br>
                <p>Gracias por usar nuestro servicio de Wallet Digital.</p>
            </body>
            </html>
            """, monto, emailRemitente);

        enviarCorreoSimple(emailDestinatario, asunto, cuerpo);
    }

    /**
     * Envía confirmación de transferencia al remitente
     */
    public void enviarConfirmacionTransferencia(String emailRemitente, String emailDestinatario, double monto) throws MessagingException {
        String asunto = "Transferencia enviada - Wallet Digital";
        String cuerpo = String.format("""
            <html>
            <body>
                <h2>Transferencia Enviada</h2>
                <p>Has enviado <strong>$%.2f</strong> a <strong>%s</strong>.</p>
                <p>La transferencia se ha procesado exitosamente.</p>
                <br>
                <p>Gracias por usar nuestro servicio de Wallet Digital.</p>
            </body>
            </html>
            """, monto, emailDestinatario);

        enviarCorreoSimple(emailRemitente, asunto, cuerpo);
    }
}
