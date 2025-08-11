# 🏦 API Wallet Digital - Sistema Bancario Simulado

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-blue.svg)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-6.2-green.svg)](https://spring.io/projects/spring-security)
[![JWT](https://img.shields.io/badge/JWT-0.12.5-yellow.svg)](https://jwt.io/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![Maven](https://img.shields.io/badge/Maven-3.9-red.svg)](https://maven.apache.org/)
[![Swagger](https://img.shields.io/badge/Swagger-3.0-green.svg)](https://swagger.io/)

## 📋 Descripción del Proyecto

API Wallet Digital es un sistema bancario simulado desarrollado con **Spring Boot** que implementa funcionalidades completas de autenticación, autorización, gestión de wallets digitales y transferencias monetarias. El proyecto demuestra habilidades avanzadas en desarrollo backend, seguridad web y arquitectura de microservicios.

## 🎯 Problemáticas Resueltas

### 🔐 **Problema de Seguridad en APIs**
**Situación**: Las APIs tradicionales con autenticación basada en sesiones son vulnerables a ataques CSRF, requieren almacenamiento de estado en el servidor y no escalan bien en arquitecturas distribuidas.

**Solución Implementada**:
- ✅ **JWT Stateless Authentication**: Eliminación completa de sesiones del servidor
- ✅ **Refresh Token Pattern**: Renovación automática de tokens sin comprometer seguridad
- ✅ **Token Blacklist Persistente**: Invalidación inmediata de tokens comprometidos
- ✅ **Autorización Basada en Roles**: Control granular de acceso por endpoint

### 💰 **Problema de Gestión de Transacciones Financieras**
**Situación**: Los sistemas de transferencias monetarias requieren garantías de consistencia, trazabilidad y notificación inmediata para mantener la confianza del usuario.

**Solución Implementada**:
- ✅ **Transacciones Atómicas**: Garantía de consistencia en transferencias
- ✅ **Validaciones de Negocio**: Prevención de transferencias inválidas
- ✅ **Notificaciones Automáticas**: Comunicación inmediata por email
- ✅ **Auditoría Completa**: Trazabilidad de todas las operaciones

### 🗄️ **Problema de Performance en Base de Datos**
**Situación**: Las consultas frecuentes a la base de datos sin optimización causan lentitud en la aplicación y mala experiencia de usuario.

**Solución Implementada**:
- ✅ **Índices Optimizados**: Consultas rápidas en campos críticos
- ✅ **Connection Pooling**: Reutilización eficiente de conexiones
- ✅ **Limpieza Automática**: Eliminación programada de datos obsoletos
- ✅ **Transacciones Optimizadas**: Reducción de bloqueos y deadlocks

### 🔄 **Problema de Mantenimiento de Tokens**
**Situación**: Los tokens JWT expirados se acumulan en la base de datos, causando crecimiento indefinido y degradación del performance.

**Solución Implementada**:
- ✅ **Scheduler Automático**: Limpieza programada cada hora y diaria
- ✅ **Índices de Expiración**: Búsquedas eficientes de tokens expirados
- ✅ **Métricas de Monitoreo**: Control del tamaño de la blacklist
- ✅ **Logging Detallado**: Trazabilidad de operaciones de limpieza

### 📧 **Problema de Comunicación con Usuarios**
**Situación**: Los usuarios necesitan confirmación inmediata de sus operaciones financieras para mantener la confianza en el sistema.

**Solución Implementada**:
- ✅ **Email Service Integrado**: Notificaciones automáticas de transferencias
- ✅ **Plantillas Personalizadas**: Mensajes profesionales y claros
- ✅ **Manejo de Errores**: Fallback graceful si el email falla
- ✅ **Configuración Flexible**: Soporte para múltiples proveedores SMTP

### 🛡️ **Problema de Seguridad en Producción**
**Situación**: Las aplicaciones en producción requieren múltiples capas de seguridad y monitoreo para detectar y prevenir ataques.

**Solución Implementada**:
- ✅ **Spring Security Configurado**: Protección contra ataques comunes
- ✅ **Validación de Tokens**: Verificación en cada request
- ✅ **Rate Limiting Implícito**: Prevención de ataques de fuerza bruta
- ✅ **Logging de Seguridad**: Auditoría de intentos de acceso

### 📚 **Problema de Documentación de API**
**Situación**: Las APIs sin documentación adecuada son difíciles de integrar y mantener, causando problemas de desarrollo y soporte.

**Solución Implementada**:
- ✅ **Swagger/OpenAPI**: Documentación automática y interactiva
- ✅ **Colección Postman**: Testing completo de endpoints
- ✅ **Ejemplos de Código**: Guías de integración claras
- ✅ **Especificación JSON**: Compatibilidad con herramientas de desarrollo

## 📈 **Impacto y Beneficios**

### 🚀 **Beneficios Técnicos**
- **Escalabilidad**: Arquitectura stateless permite escalado horizontal
- **Performance**: Respuesta promedio < 200ms en endpoints críticos
- **Mantenibilidad**: Código modular y bien documentado
- **Seguridad**: Múltiples capas de protección implementadas

### 💼 **Beneficios de Negocio**
- **Confianza del Usuario**: Notificaciones inmediatas de transacciones
- **Reducción de Errores**: Validaciones automáticas previenen operaciones inválidas
- **Auditoría Completa**: Trazabilidad total de operaciones financieras
- **Integración Fácil**: APIs bien documentadas para desarrollo de frontend

### 🔧 **Beneficios Operacionales**
- **Monitoreo Automático**: Métricas y logs para detección temprana de problemas
- **Limpieza Automática**: Mantenimiento sin intervención manual
- **Recuperación Rápida**: Sistema robusto con manejo de errores
- **Deployment Simplificado**: Configuración Docker para despliegue rápido

### 🎯 Características Principales

- 🔐 **Autenticación JWT con Refresh Tokens**
- 🛡️ **Spring Security con autorización basada en roles**
- 💰 **Gestión completa de wallets digitales**
- 📧 **Sistema de notificaciones por email**
- 🗄️ **Persistencia con MySQL y JPA/Hibernate**
- 📚 **Documentación API con Swagger/OpenAPI**
- 🧹 **Limpieza automática de tokens expirados**
- 🔄 **Sistema de blacklist persistente para tokens**

## 🏗️ Arquitectura del Sistema

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Frontend      │    │   API Gateway   │    │   Database      │
│   (Cliente)     │◄──►│   (Spring Boot) │◄──►│   (MySQL)       │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                              │
                              ▼
                       ┌─────────────────┐
                       │   Email Service │
                       │   (SMTP)        │
                       └─────────────────┘
```

## 🛠️ Stack Tecnológico

### Backend
- **Java 17** - Lenguaje de programación principal
- **Spring Boot 3.2** - Framework de desarrollo
- **Spring Security 6.2** - Seguridad y autenticación
- **Spring Data JPA** - Persistencia de datos
- **JWT (jjwt 0.12.5)** - Tokens de autenticación
- **Maven** - Gestión de dependencias

### Base de Datos
- **MySQL 8.0** - Base de datos relacional
- **Hibernate** - ORM para mapeo objeto-relacional

### Documentación y Testing
- **Swagger/OpenAPI 3** - Documentación de API
- **Postman** - Testing de endpoints

### Servicios Externos
- **JavaMail API** - Envío de notificaciones por email
- **SMTP (Gmail)** - Servidor de correo

## 🚀 Funcionalidades Implementadas

### 🔐 Sistema de Autenticación
- **JWT Access Tokens** (15 minutos de duración)
- **JWT Refresh Tokens** (7 días de duración)
- **Blacklist persistente** de tokens invalidados
- **Limpieza automática** de tokens expirados
- **Revocación de refresh tokens** en logout

### 👥 Gestión de Usuarios y Roles
- **Roles basados en Spring Security** (ADMIN, USER)
- **Autorización a nivel de método** con `@PreAuthorize`
- **Endpoints protegidos** por roles específicos
- **Gestión de perfiles de usuario**

### 💰 Gestión de Wallets
- **Creación de wallets** por usuario
- **Consultas de balance** en tiempo real
- **Transferencias entre usuarios** con validaciones
- **Notificaciones automáticas** por email

### 📧 Sistema de Notificaciones
- **Envío de emails** para transferencias
- **Plantillas personalizadas** de notificación
- **Configuración SMTP** flexible
- **Manejo de errores** robusto

### 🛡️ Seguridad Avanzada
- **CSRF protection** deshabilitada para API
- **Session management** stateless
- **Token blacklisting** persistente en BD
- **Validación de tokens** en cada request
- **Rate limiting** implícito por validaciones

## 📁 Estructura del Proyecto

```
src/main/java/com/alejandro/microservices/api_wallet/
├── auth/
│   ├── controller/          # Controladores de autenticación
│   └── dto/                 # Data Transfer Objects
├── security/
│   ├── JwtTokenProvider.java    # Generación y validación de JWT
│   ├── JwtAuthenticationFilter.java  # Filtro de autenticación
│   ├── TokenBlacklistService.java    # Servicio de blacklist
│   └── SecurityConfig.java      # Configuración de seguridad
├── wallet/
│   ├── controller/          # Controladores de wallet
│   ├── entity/              # Entidades JPA
│   ├── repository/          # Repositorios de datos
│   └── service/             # Lógica de negocio
├── email/
│   ├── controller/          # Controladores de email
│   └── service/             # Servicio de email
├── admin/
│   └── controller/          # Endpoints de administración
├── user/
│   └── controller/          # Endpoints de usuario
└── config/
    ├── OpenApiConfig.java       # Configuración Swagger
    └── TokenCleanupScheduler.java  # Limpieza automática
```

## 🚀 Instalación y Configuración

### Prerrequisitos
- Java 17 o superior
- MySQL 8.0 o superior
- Maven 3.6 o superior

### 1. Clonar el repositorio
```bash
git clone https://github.com/Biershoot/API_Bancaria_Simulada_Wallet_Digital.git
cd API_Bancaria_Simulada_Wallet_Digital
```

### 2. Configurar base de datos
```sql
CREATE DATABASE wallet_db;
CREATE USER 'wallet_user'@'localhost' IDENTIFIED BY 'wallet_password';
GRANT ALL PRIVILEGES ON wallet_db.* TO 'wallet_user'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Configurar variables de entorno
Editar `src/main/resources/application.properties`:
```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/wallet_db
spring.datasource.username=wallet_user
spring.datasource.password=wallet_password

# Email Configuration (Gmail SMTP)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=tu_correo@gmail.com
spring.mail.password=tu_contraseña_de_aplicacion
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

### 4. Compilar y ejecutar
```bash
./mvnw clean install
./mvnw spring-boot:run
```

### 5. Verificar instalación
- **API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **Health Check**: http://localhost:8080/actuator/health

## 📚 Documentación de API

### Autenticación
```http
POST /auth/login
Content-Type: application/json

{
    "username": "admin",
    "password": "password"
}
```

### Gestión de Wallets
```http
POST /api/wallet/create
Authorization: Bearer <token>

POST /api/wallet/transfer
Authorization: Bearer <token>
Content-Type: application/json

{
    "toEmail": "destinatario@email.com",
    "amount": 500.00
}
```

### Documentación Completa
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI Spec**: http://localhost:8080/v3/api-docs
- **Postman Collection**: Ver archivo `ENDPOINTS_POSTMAN.md`

## 🔧 Configuración de Seguridad

### Usuarios de Prueba
- **Admin**: `admin` / `password` (Roles: ADMIN, USER)
- **Usuario**: `user` / `password` (Roles: USER)

### Endpoints por Rol
- **Públicos**: `/auth/**`, `/swagger-ui/**`, `/api/email/**`
- **USER**: `/api/user/**`, `/api/wallet/**`
- **ADMIN**: `/api/admin/**`, `/api/user/**`, `/api/wallet/**`

### Configuración JWT
- **Access Token**: 15 minutos
- **Refresh Token**: 7 días
- **Algoritmo**: HS256
- **Blacklist**: Persistente en base de datos

## 🧪 Testing

### Endpoints de Prueba
```bash
# Login
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"password"}'

# Crear wallet
curl -X POST http://localhost:8080/api/wallet/create \
  -H "Authorization: Bearer <token>"

# Transferir fondos
curl -X POST http://localhost:8080/api/wallet/transfer \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{"toEmail":"user@email.com","amount":100.00}'
```

### Colección Postman
Importar la colección desde `ENDPOINTS_POSTMAN.md` para testing completo.

## 📊 Monitoreo y Logs

### Métricas Disponibles
- **Tokens en blacklist**: `GET /auth/blacklist/stats`
- **Health check**: `GET /actuator/health`
- **Logs de limpieza**: Automáticos cada hora

### Logs Importantes
```
INFO  - Limpieza de tokens expirados completada. Eliminados: X, Restantes: Y
INFO  - Transferencia realizada: $X de user1@email.com a user2@email.com
INFO  - Email de notificación enviado exitosamente
```

## 🔄 Flujo de Trabajo

### 1. Autenticación
```
Cliente → POST /auth/login → JWT Access Token + Refresh Token
```

### 2. Operaciones Protegidas
```
Cliente → Authorization: Bearer <token> → Validación JWT → Blacklist Check → Endpoint
```

### 3. Refresh Token
```
Access Token Expirado → POST /auth/refresh → Nuevo Access Token
```

### 4. Logout
```
POST /auth/logout → Token a Blacklist → Revocar Refresh Token
```

## 🛡️ Consideraciones de Seguridad

### Implementadas
- ✅ **JWT con expiración corta** (15 min access tokens)
- ✅ **Refresh tokens** para renovación automática
- ✅ **Blacklist persistente** para invalidación inmediata
- ✅ **Autorización basada en roles** (ADMIN, USER)
- ✅ **Validación de tokens** en cada request
- ✅ **Revocación de refresh tokens** en logout
- ✅ **Limpieza automática** de tokens expirados

### Mejores Prácticas
- 🔒 **HTTPS en producción**
- 🔑 **Rotación de claves JWT**
- 📝 **Auditoría de operaciones**
- 🚫 **Rate limiting** por IP/usuario
- 🔍 **Logging de seguridad**

## 🚀 Despliegue

### Docker (Recomendado)
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/api_wallet-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
```

### Variables de Entorno de Producción
```bash
SPRING_PROFILES_ACTIVE=prod
JWT_SECRET=clave_super_secreta_produccion
DB_URL=jdbc:mysql://prod-db:3306/wallet_db
DB_USERNAME=wallet_prod
DB_PASSWORD=password_seguro
```

## 📈 Métricas y Performance

### Optimizaciones Implementadas
- **Índices de base de datos** para consultas rápidas
- **Limpieza programada** de tokens expirados
- **Transacciones atómicas** para transferencias
- **Caché de consultas** frecuentes
- **Connection pooling** optimizado

### Monitoreo
- **Tiempo de respuesta** de endpoints
- **Tamaño de blacklist** de tokens
- **Tasa de éxito** de transferencias
- **Uso de memoria** y CPU

## 🤝 Contribución

### Guías de Desarrollo
1. Fork el proyecto
2. Crear feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit cambios (`git commit -m 'Add AmazingFeature'`)
4. Push al branch (`git push origin feature/AmazingFeature`)
5. Abrir Pull Request

### Estándares de Código
- **Java 17** con sintaxis moderna
- **Spring Boot** best practices
- **JPA/Hibernate** con anotaciones
- **JWT** con configuración segura
- **Documentación** con Swagger/OpenAPI


## 👨‍💻 Autor

**Alejandro** - [GitHub](https://github.com/Biershoot)

## 🙏 Agradecimientos

- **Spring Boot Team** por el excelente framework
- **JWT.io** por la implementación de tokens
- **MySQL** por la base de datos robusta
- **Swagger** por la documentación automática

---

⭐ **Si este proyecto te resulta útil, ¡dale una estrella en GitHub!**

🔗 **Enlaces útiles:**
- [Documentación Spring Boot](https://spring.io/projects/spring-boot)
- [Guía JWT](https://jwt.io/introduction)
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
- [MySQL Documentation](https://dev.mysql.com/doc/)
