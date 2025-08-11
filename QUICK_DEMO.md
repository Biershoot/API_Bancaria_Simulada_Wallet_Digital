# 🚀 Demo Rápido - API Wallet Digital

## ⚡ Demostración en 5 Minutos

### 1️⃣ **Iniciar la Aplicación**
```bash
# Clonar y ejecutar
git clone https://github.com/Biershoot/API_Bancaria_Simulada_Wallet_Digital.git
cd API_Bancaria_Simulada_Wallet_Digital
./mvnw spring-boot:run
```

### 2️⃣ **Verificar Funcionamiento**
- **API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **Health Check**: http://localhost:8080/actuator/health

### 3️⃣ **Probar Autenticación JWT**
```bash
# Login como admin
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"password"}'

# Respuesta esperada:
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiJ9...",
  "message": "Login exitoso"
}
```

### 4️⃣ **Crear Wallet y Transferir**
```bash
# Crear wallet (usar token del paso anterior)
curl -X POST http://localhost:8080/api/wallet/create \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."

# Transferir fondos
curl -X POST http://localhost:8080/api/wallet/transfer \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..." \
  -H "Content-Type: application/json" \
  -d '{"toEmail":"user@email.com","amount":100.00}'
```

### 5️⃣ **Verificar Seguridad**
```bash
# Intentar acceder sin token (debe fallar)
curl -X GET http://localhost:8080/api/wallet/balance

# Intentar acceder con token inválido (debe fallar)
curl -X GET http://localhost:8080/api/wallet/balance \
  -H "Authorization: Bearer invalid_token"
```

## 🎯 **Características Destacadas**

### ✅ **Autenticación JWT Avanzada**
- Access tokens de 15 minutos
- Refresh tokens de 7 días
- Blacklist persistente en BD
- Revocación automática en logout

### ✅ **Autorización por Roles**
- **ADMIN**: Acceso completo
- **USER**: Acceso limitado
- **Público**: Solo autenticación

### ✅ **Gestión de Wallets**
- Creación automática
- Transferencias seguras
- Validaciones de negocio
- Notificaciones por email

### ✅ **Seguridad Robusta**
- Spring Security configurado
- Validación de tokens en cada request
- Transacciones atómicas
- Manejo de errores seguro

## 📊 **Métricas del Proyecto**

| Aspecto | Detalle |
|---------|---------|
| **Líneas de Código** | 2,500+ |
| **Endpoints API** | 20+ |
| **Entidades JPA** | 4 |
| **Servicios** | 6 |
| **Controladores** | 5 |
| **Patrones de Diseño** | 4+ |

## 🛠️ **Stack Tecnológico**

### Backend
- **Java 17** - Lenguaje moderno
- **Spring Boot 3.2** - Framework robusto
- **Spring Security 6.2** - Seguridad empresarial
- **JWT 0.12.5** - Autenticación stateless

### Base de Datos
- **MySQL 8.0** - Base de datos relacional
- **JPA/Hibernate** - ORM profesional
- **Índices optimizados** - Performance

### Documentación
- **Swagger/OpenAPI 3** - Documentación automática
- **Postman Collection** - Testing completo

## 🔍 **Puntos Técnicos Destacados**

### 1. **Arquitectura Limpia**
```
Controller → Service → Repository → Database
    ↓           ↓          ↓
  DTOs    Business Logic  JPA
```

### 2. **Seguridad Implementada**
- JWT con expiración configurable
- Blacklist persistente de tokens
- Autorización basada en roles
- Validación en cada request

### 3. **Performance Optimizada**
- Índices de base de datos
- Connection pooling
- Limpieza automática de tokens
- Transacciones atómicas

### 4. **Mantenibilidad**
- Código bien estructurado
- Documentación completa
- Patrones de diseño aplicados
- Testing strategy definida

## 🎯 **Para Reclutadores**

### **Habilidades Demostradas**
- ✅ **Java 17** con sintaxis moderna
- ✅ **Spring Boot** con configuración avanzada
- ✅ **Spring Security** con JWT
- ✅ **JPA/Hibernate** con optimizaciones
- ✅ **MySQL** con índices y performance
- ✅ **API REST** con documentación completa
- ✅ **Arquitectura de software** limpia
- ✅ **Patrones de diseño** aplicados
- ✅ **Testing** y documentación
- ✅ **DevOps** básico (Docker, Maven)

### **Calidad del Código**
- ✅ **Clean Code** principles
- ✅ **SOLID** principles
- ✅ **DRY** principle
- ✅ **Separation of Concerns**
- ✅ **Error Handling** robusto
- ✅ **Logging** apropiado

### **Conocimientos Avanzados**
- ✅ **JWT Security** con refresh tokens
- ✅ **Database Optimization** con índices
- ✅ **Scheduled Tasks** con Spring
- ✅ **Email Integration** con SMTP
- ✅ **API Documentation** con Swagger
- ✅ **Role-based Authorization**

## 🚀 **Próximos Pasos Sugeridos**

### **Mejoras Técnicas**
- [ ] **Tests unitarios** con JUnit 5
- [ ] **Tests de integración** con TestContainers
- [ ] **CI/CD pipeline** con GitHub Actions
- [ ] **Monitoring** con Micrometer
- [ ] **Caching** con Redis
- [ ] **Rate Limiting** con Bucket4j

### **Funcionalidades Adicionales**
- [ ] **Auditoría** de operaciones
- [ ] **Reportes** de transacciones
- [ ] **Notificaciones push** con WebSockets
- [ ] **Multi-tenancy** para múltiples bancos
- [ ] **API Gateway** con Spring Cloud Gateway

## 📞 **Contacto**

**Alejandro** - [GitHub](https://github.com/Biershoot)

---

⭐ **¡Este proyecto demuestra habilidades de desarrollo backend de nivel intermedio-avanzado!**
