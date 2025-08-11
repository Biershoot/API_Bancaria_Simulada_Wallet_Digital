package com.alejandro.microservices.api_wallet.security;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class TokenBlacklistService {

    // Usando ConcurrentHashMap para thread-safety
    private final ConcurrentMap<String, Long> blacklistedTokens = new ConcurrentHashMap<>();

    /**
     * Agrega un token a la lista negra
     */
    public void blacklistToken(String token) {
        blacklistedTokens.put(token, System.currentTimeMillis());
    }

    /**
     * Verifica si un token está en la lista negra
     */
    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.containsKey(token);
    }

    /**
     * Limpia tokens expirados de la lista negra (opcional, para mantener memoria limpia)
     */
    public void cleanupExpiredTokens() {
        long currentTime = System.currentTimeMillis();
        // Limpiar tokens más antiguos de 24 horas
        long expirationTime = 24 * 60 * 60 * 1000; // 24 horas en milisegundos
        
        blacklistedTokens.entrySet().removeIf(entry -> 
            (currentTime - entry.getValue()) > expirationTime
        );
    }

    /**
     * Obtiene el tamaño de la lista negra (para monitoreo)
     */
    public int getBlacklistSize() {
        return blacklistedTokens.size();
    }
}
