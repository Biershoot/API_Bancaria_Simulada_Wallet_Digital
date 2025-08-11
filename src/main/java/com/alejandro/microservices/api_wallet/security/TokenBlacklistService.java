package com.alejandro.microservices.api_wallet.security;

import com.alejandro.microservices.api_wallet.wallet.entity.BlacklistedToken;
import com.alejandro.microservices.api_wallet.wallet.repository.BlacklistedTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class TokenBlacklistService {

    private final BlacklistedTokenRepository blacklistedTokenRepository;

    public TokenBlacklistService(BlacklistedTokenRepository blacklistedTokenRepository) {
        this.blacklistedTokenRepository = blacklistedTokenRepository;
    }

    /**
     * Agrega un token a la lista negra con su fecha de expiración
     */
    @Transactional
    public void blacklistToken(String token, Instant expiresAt) {
        if (blacklistedTokenRepository.existsByToken(token)) {
            return; // Token ya está en la blacklist
        }
        
        BlacklistedToken blacklistedToken = BlacklistedToken.builder()
                .token(token)
                .expiresAt(expiresAt)
                .createdAt(Instant.now())
                .build();
        
        blacklistedTokenRepository.save(blacklistedToken);
    }

    /**
     * Agrega un token a la lista negra (método legacy para compatibilidad)
     */
    @Transactional
    public void blacklistToken(String token) {
        // Si no tenemos la fecha de expiración, asumimos 24 horas
        Instant expiresAt = Instant.now().plusSeconds(24 * 60 * 60);
        blacklistToken(token, expiresAt);
    }

    /**
     * Verifica si un token está en la lista negra
     */
    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokenRepository.existsByToken(token);
    }

    /**
     * Elimina tokens expirados de la lista negra
     */
    @Transactional
    public void removeExpired() {
        blacklistedTokenRepository.deleteByExpiresAtBefore(Instant.now());
    }

    /**
     * Obtiene el tamaño de la lista negra (para monitoreo)
     */
    public long getBlacklistSize() {
        return blacklistedTokenRepository.count();
    }
}
