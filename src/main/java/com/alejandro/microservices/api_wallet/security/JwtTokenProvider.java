package com.alejandro.microservices.api_wallet.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

/**
 * 🔐 JWT Token Provider - Componente Core de Seguridad
 * 
 * Este componente implementa la generación, validación y gestión de tokens JWT
 * siguiendo las mejores prácticas de seguridad para APIs REST.
 * 
 * 🎯 Características Implementadas:
 * - Generación de Access Tokens (15 min) y Refresh Tokens (7 días)
 * - Validación robusta con manejo de excepciones
 * - Uso de algoritmos criptográficos seguros (HS256)
 * - Extracción segura de claims del token
 * 
 * 🛡️ Seguridad:
 * - Tokens de corta duración para minimizar exposición
 * - Refresh tokens para renovación automática
 * - Validación completa de firma y expiración
 * - Manejo seguro de excepciones sin información sensible
 * 
 * 📊 Métricas de Seguridad:
 * - Access Token: 15 minutos (balance entre seguridad y UX)
 * - Refresh Token: 7 días (conveniencia del usuario)
 * - Algoritmo: HS256 (estándar de la industria)
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
@Component
public class JwtTokenProvider {

    // 🔑 Configuración de Seguridad - En producción usar variables de entorno
    private final String SECRET = "EstaEsUnaClaveMuySeguraParaJWT1234567890";
    
    // ⏰ Configuración de Expiración - Balance entre seguridad y experiencia de usuario
    private final long ACCESS_TOKEN_EXPIRATION = 900000; // 15 minutos (15 * 60 * 1000)
    private final long REFRESH_TOKEN_EXPIRATION = 604800000; // 7 días (7 * 24 * 60 * 60 * 1000)

    /**
     * 🔑 Genera la clave de firma para JWT usando HMAC-SHA256
     * 
     * Esta clave se usa para firmar y verificar todos los tokens JWT.
     * En producción, esta clave debería almacenarse en variables de entorno
     * o en un servicio de gestión de secretos (AWS Secrets Manager, HashiCorp Vault).
     * 
     * @return SecretKey para firmar tokens JWT
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    /**
     * 🚀 Genera un Access Token JWT para autenticación de corta duración
     * 
     * Los access tokens tienen una duración corta (15 minutos) para minimizar
     * el riesgo de exposición. Si un token es comprometido, el impacto es limitado.
     * 
     * 🎯 Casos de Uso:
     * - Autenticación en cada request a la API
     * - Acceso a recursos protegidos
     * - Operaciones sensibles (transferencias, consultas de balance)
     * 
     * @param username Identificador único del usuario
     * @return JWT Access Token firmado
     */
    public String generarAccessToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * 🔄 Genera un Refresh Token JWT para renovación automática
     * 
     * Los refresh tokens tienen una duración larga (7 días) y se usan para
     * obtener nuevos access tokens sin requerir re-autenticación del usuario.
     * 
     * 🛡️ Seguridad:
     * - Almacenado de forma segura en la base de datos
     * - Revocado inmediatamente en logout
     * - Validado contra la base de datos en cada uso
     * 
     * @param username Identificador único del usuario
     * @return JWT Refresh Token firmado
     */
    public String generarRefreshToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * 🔄 Método legacy para compatibilidad con código existente
     * 
     * @deprecated Usar generarAccessToken() para mayor claridad
     * @param username Identificador único del usuario
     * @return JWT Access Token firmado
     */
    @Deprecated
    public String generarToken(String username) {
        return generarAccessToken(username);
    }

    /**
     * 🔍 Extrae el username del token JWT de forma segura
     * 
     * Este método valida la firma del token antes de extraer el subject,
     * asegurando que el token no ha sido manipulado.
     * 
     * ⚠️ Importante: Siempre validar el token antes de usar el username
     * 
     * @param token JWT token a procesar
     * @return Username extraído del token
     * @throws JwtException si el token es inválido
     */
    public String obtenerUsernameDelToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    /**
     * ✅ Valida si el token JWT es válido y no ha expirado
     * 
     * Esta validación incluye:
     * - Verificación de la firma criptográfica
     * - Validación de la fecha de expiración
     * - Verificación del formato del token
     * 
     * 🛡️ Manejo de Errores:
     * - JwtException: Token malformado o manipulado
     * - IllegalArgumentException: Token nulo o vacío
     * - ExpiredJwtException: Token expirado
     * 
     * @param token JWT token a validar
     * @return true si el token es válido, false en caso contrario
     */
    public boolean validarToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // 🔒 Log de seguridad (en producción usar logger apropiado)
            // logger.warn("Token validation failed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * ⏰ Verifica si el token JWT ha expirado
     * 
     * Útil para:
     * - Limpieza de tokens expirados
     * - Decisión de renovación de tokens
     * - Auditoría de seguridad
     * 
     * @param token JWT token a verificar
     * @return true si el token ha expirado, false si es válido
     */
    public boolean tokenExpirado(String token) {
        try {
            Date expiration = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration();
            return expiration.before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            // 🔒 Si no se puede parsear, considerar como expirado por seguridad
            return true;
        }
    }

    /**
     * 📅 Obtiene la fecha de expiración del token JWT
     * 
     * Útil para:
     * - Auditoría de tokens
     * - Monitoreo de expiración
     * - Gestión de blacklist
     * 
     * @param token JWT token a procesar
     * @return Date de expiración del token
     * @throws JwtException si el token es inválido
     */
    public Date obtenerFechaExpiracion(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }

    /**
     * ⏱️ Obtiene la fecha de expiración como Instant (Java 8+)
     * 
     * Más útil para operaciones con tiempo modernas y comparaciones
     * 
     * @param token JWT token a procesar
     * @return Instant de expiración del token
     * @throws JwtException si el token es inválido
     */
    public Instant obtenerFechaExpiracionInstant(String token) {
        Date expiration = obtenerFechaExpiracion(token);
        return expiration.toInstant();
    }
}
