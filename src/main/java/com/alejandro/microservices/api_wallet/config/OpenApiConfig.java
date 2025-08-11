package com.alejandro.microservices.api_wallet.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(new Info()
                        .title("🏦 API Wallet Digital - Sistema Bancario Simulado")
                        .description("""
                                ## 📋 Descripción del Proyecto
                                
                                API Wallet Digital es un sistema bancario simulado desarrollado con Spring Boot que implementa funcionalidades completas de autenticación, autorización, gestión de wallets digitales y transferencias monetarias. El proyecto demuestra habilidades avanzadas en desarrollo backend, seguridad web y arquitectura de microservicios.
                                
                                ### 🔐 Características Principales
                                - **JWT Authentication** con Access & Refresh Tokens
                                - **Spring Security** con autorización basada en roles
                                - **Gestión de Wallets** con transferencias seguras
                                - **Notificaciones por Email** automáticas
                                - **Token Blacklist** persistente en base de datos
                                - **Limpieza Automática** de tokens expirados
                                
                                ### 🛠️ Stack Tecnológico
                                - Java 17, Spring Boot 3.2, Spring Security 6.2
                                - JWT (jjwt 0.12.5), MySQL 8.0, JPA/Hibernate
                                - Swagger/OpenAPI 3, JavaMail API
                                
                                ### 👥 Usuarios de Prueba
                                - **Admin**: `admin` / `password` (Roles: ADMIN, USER)
                                - **Usuario**: `user` / `password` (Roles: USER)
                                
                                📚 **Documentación completa**: README.md, TECHNICAL_DETAILS.md, QUICK_DEMO.md
                                """)
                        .version("1.0.0"));
    }
}
