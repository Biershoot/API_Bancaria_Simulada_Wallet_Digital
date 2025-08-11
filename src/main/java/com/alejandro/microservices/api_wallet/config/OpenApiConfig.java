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
                                
                                API Wallet Digital es un sistema bancario simulado desarrollado con **Spring Boot** que implementa funcionalidades completas de autenticación, autorización, gestión de wallets digitales y transferencias monetarias. El proyecto demuestra habilidades avanzadas en desarrollo backend, seguridad web y arquitectura de microservicios.
                                
                                ## 🎯 Problemáticas Resueltas
                                
                                ### 🔐 **Seguridad en APIs**
                                - JWT Stateless Authentication con Refresh Tokens
                                - Token Blacklist Persistente para invalidación inmediata
                                - Autorización Basada en Roles (ADMIN, USER)
                                
                                ### 💰 **Gestión de Transacciones Financieras**
                                - Transacciones Atómicas con garantía de consistencia
                                - Validaciones de Negocio para prevenir operaciones inválidas
                                - Notificaciones Automáticas por email
                                - Auditoría Completa de todas las operaciones
                                
                                ### 🗄️ **Performance y Optimización**
                                - Índices Optimizados para consultas rápidas
                                - Connection Pooling para reutilización eficiente
                                - Limpieza Automática de datos obsoletos
                                - Scheduler para mantenimiento programado
                                
                                ## 🛠️ Stack Tecnológico
                                
                                - **Java 17** - Lenguaje de programación principal
                                - **Spring Boot 3.2** - Framework de desarrollo
                                - **Spring Security 6.2** - Seguridad y autenticación
                                - **JWT (jjwt 0.12.5)** - Tokens de autenticación
                                - **MySQL 8.0** - Base de datos relacional
                                - **Swagger/OpenAPI 3** - Documentación automática
                                
                                ## 🚀 Funcionalidades Principales
                                
                                - 🔐 **Autenticación JWT** con Access Tokens (15 min) y Refresh Tokens (7 días)
                                - 🛡️ **Spring Security** con autorización basada en roles
                                - 💰 **Gestión completa de wallets** digitales con transferencias
                                - 📧 **Sistema de notificaciones** por email automático
                                - 🧹 **Limpieza automática** de tokens expirados
                                - 🔄 **Sistema de blacklist** persistente para tokens
                                
                                ## 📊 Métricas del Proyecto
                                
                                - **Líneas de código**: 2,500+ líneas
                                - **Endpoints API**: 20+ endpoints documentados
                                - **Entidades JPA**: 4 entidades principales
                                - **Servicios**: 6 servicios con lógica de negocio
                                - **Controladores**: 5 controladores REST
                                
                                ## 🔧 Configuración de Seguridad
                                
                                ### Usuarios de Prueba
                                - **Admin**: `admin` / `password` (Roles: ADMIN, USER)
                                - **Usuario**: `user` / `password` (Roles: USER)
                                
                                ### Endpoints por Rol
                                - **Públicos**: `/auth/**`, `/swagger-ui/**`, `/api/email/**`
                                - **USER**: `/api/user/**`, `/api/wallet/**`
                                - **ADMIN**: `/api/admin/**`, `/api/user/**`, `/api/wallet/**`
                                
                                ## 📚 Documentación Completa
                                
                                Para más detalles técnicos, ver los archivos:
                                - `README.md` - Documentación general del proyecto
                                - `TECHNICAL_DETAILS.md` - Detalles técnicos avanzados
                                - `QUICK_DEMO.md` - Guía de demostración rápida
                                - `ENDPOINTS_POSTMAN.md` - Colección de Postman
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Alejandro")
                                .email("alejandro@example.com")
                                .url("https://github.com/Biershoot")));
    }
}
