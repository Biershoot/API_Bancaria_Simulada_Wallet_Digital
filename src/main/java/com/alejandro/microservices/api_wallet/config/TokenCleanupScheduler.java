package com.alejandro.microservices.api_wallet.config;

import com.alejandro.microservices.api_wallet.security.TokenBlacklistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TokenCleanupScheduler {

    private static final Logger logger = LoggerFactory.getLogger(TokenCleanupScheduler.class);
    
    private final TokenBlacklistService tokenBlacklistService;

    public TokenCleanupScheduler(TokenBlacklistService tokenBlacklistService) {
        this.tokenBlacklistService = tokenBlacklistService;
    }

    /**
     * Limpia tokens expirados de la blacklist cada hora
     * Cron: segundo minuto hora día mes día_semana
     * "0 0 * * * *" = cada hora en el minuto 0
     */
    @Scheduled(cron = "0 0 * * * *")
    public void cleanupExpiredTokens() {
        try {
            long beforeCount = tokenBlacklistService.getBlacklistSize();
            tokenBlacklistService.removeExpired();
            long afterCount = tokenBlacklistService.getBlacklistSize();
            long removedCount = beforeCount - afterCount;
            
            logger.info("Limpieza de tokens expirados completada. Eliminados: {}, Restantes: {}", 
                       removedCount, afterCount);
        } catch (Exception e) {
            logger.error("Error durante la limpieza de tokens expirados", e);
        }
    }

    /**
     * Limpia tokens expirados cada día a las 2:00 AM
     * Útil para limpieza más agresiva durante horas de bajo tráfico
     */
    @Scheduled(cron = "0 0 2 * * *")
    public void dailyCleanup() {
        try {
            long beforeCount = tokenBlacklistService.getBlacklistSize();
            tokenBlacklistService.removeExpired();
            long afterCount = tokenBlacklistService.getBlacklistSize();
            long removedCount = beforeCount - afterCount;
            
            logger.info("Limpieza diaria de tokens expirados completada. Eliminados: {}, Restantes: {}", 
                       removedCount, afterCount);
        } catch (Exception e) {
            logger.error("Error durante la limpieza diaria de tokens expirados", e);
        }
    }
}
